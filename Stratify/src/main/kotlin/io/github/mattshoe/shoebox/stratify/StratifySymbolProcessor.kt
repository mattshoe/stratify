package io.github.mattshoe.shoebox.stratify

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import io.github.mattshoe.shoebox.stratify.dispatchers.StratifyDispatcher
import io.github.mattshoe.shoebox.stratify.ksp.StratifyCodeGeneratorImpl
import io.github.mattshoe.shoebox.stratify.ksp.StratifyCodeGenerator
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolverImpl
import io.github.mattshoe.shoebox.stratify.logger.StratifyLogger
import io.github.mattshoe.shoebox.stratify.model.GeneratedFile
import io.github.mattshoe.shoebox.stratify.processor.Processor
import io.github.mattshoe.shoebox.stratify.strategy.Strategy
import kotlinx.coroutines.*

abstract class StratifySymbolProcessor: SymbolProcessor {
    protected val environment = SymbolProcessorEnvironment(
        stratifySymbolProcessorEnvironment.kspEnvironment.options,
        stratifySymbolProcessorEnvironment.kspEnvironment.kotlinVersion,
        StratifyCodeGeneratorImpl(stratifySymbolProcessorEnvironment.kspEnvironment.codeGenerator),
        StratifyLogger(stratifySymbolProcessorEnvironment.kspEnvironment.logger),
        stratifySymbolProcessorEnvironment.kspEnvironment.apiVersion,
        stratifySymbolProcessorEnvironment.kspEnvironment.compilerVersion,
        stratifySymbolProcessorEnvironment.kspEnvironment.platforms,
        stratifySymbolProcessorEnvironment.kspEnvironment.kspVersion,
    )
    protected val codeGenerator: StratifyCodeGenerator = StratifyCodeGenerator(environment.codeGenerator)
    protected val logger: KSPLogger = environment.logger

    protected abstract suspend fun buildStrategies(resolver: StratifyResolver): List<Strategy<KSNode, out KSNode>>

    final override fun process(resolver: Resolver): List<KSAnnotated> = runBlocking(SupervisorJob() + StratifyDispatcher.Main) {
        val stratifyResolver = StratifyResolver(
            StratifyResolverImpl(resolver)
        )
        val failedNodes = mutableListOf<KSAnnotated>()

        buildStrategies(stratifyResolver).forEach { strategy ->
            strategy.processors.forEach { processor ->
                launch(StratifyDispatcher.Main) {
                    strategy.resolveNodes(stratifyResolver, processor)
                        .filterIsInstance(processor.targetClass.java)
                        .forEach { node ->
                            launch(Dispatchers.Default) {
                                try {
                                    logger.warn("processing $node")
                                    processNode(node, processor)
                                } catch (e: Throwable) {
                                    ensureActive()
                                    onError(node, e)?.let {
                                        failedNodes.add(it)
                                    }
                                }
                            }
                        }
                }
            }
        }

        return@runBlocking failedNodes
    }

    final override fun onError() {
        super.onError()
        onError(null, null)
    }

    /**
     * This method will be invoked when an uncaught processing error occurs.
     *
     * By default, a failed node will be retried in the next round of processing. To change this behavior,
     * override this method and return null.
     *
     * @param node the node during whose processing the error occurred
     * @param error the error that was caught
     *
     * @return Only return a node here if you wish to retry processing for this node. This will only work for
     *      nodes that are annotated, as they must be of type KSAnnotated
     */
    protected open fun onError(node: KSNode?, error: Throwable?): KSAnnotated? {
        return (node as? KSAnnotated)?.also { annotatedNode ->
            logger.warn("Processing failed at node '${annotatedNode}'; Stratify will retry in the next round of processing\n\tError Encountered:  $error\n", node)
        }
    }

    /**
     * Given the [fileData] produced by processing the given [node], write the [fileData] to the proper file.
     *
     * Override this method to handle your own file writing logic.
     *
     * @param node the [KSNode] whose processing produced [fileData].
     * @param fileData the file data produced by processing [KSNode].
     */
    protected open suspend fun writeToFile(node: KSNode, fileData: GeneratedFile) = withContext(StratifyDispatcher.Main) {
        val sourceFile = (node as? KSFile)
            ?: node.containingFile
            ?: throw IllegalStateException("Unable to find source file for ${node}!")
        codeGenerator.use {
            createNewFile(
                fileData.dependencies ?: Dependencies(false, sourceFile),
                fileData.packageName,
                fileData.fileName
            )
        }.bufferedWriter().use { writer ->
            writer.write(fileData.output)
        }
    }

    private suspend fun processNode(
        node: KSNode,
        processor: Processor<KSNode>
    ) = coroutineScope {
        processor.process(node)
            .forEach { fileData ->
                launch(Dispatchers.IO) {
                    writeToFile(node, fileData)
                }
            }
    }

}