package io.github.mattshoe.shoebox.stratify

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.model.GeneratedFile
import io.github.mattshoe.shoebox.stratify.processor.Processor
import io.github.mattshoe.shoebox.stratify.strategy.Strategy
import kotlinx.coroutines.*

abstract class StratifySymbolProcessor(
    protected val environment: SymbolProcessorEnvironment
): SymbolProcessor {
    protected val codeGenerator: CodeGenerator = environment.codeGenerator
    protected val logger: KSPLogger = environment.logger

    protected abstract suspend fun buildStrategies(resolver: Resolver): List<Strategy<KSNode, out KSNode>>

    final override fun process(resolver: Resolver): List<KSAnnotated> = runBlocking {
        return@runBlocking buildList {
            buildStrategies(resolver).forEach { strategy ->
                strategy.processors.forEach { processor ->
                    launch {
                        strategy.resolveNodes(resolver, processor)
                            .filterIsInstance(processor.targetClass.java)
                            .forEach { node ->
                                launch {
                                    try {
                                        processNode(node, processor)
                                    } catch (e: Throwable) {
                                        onError(node, e)?.let {
                                            this@buildList.add(it)
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    final override fun onError() {
        super.onError()
        onError(null, null)
    }

    /**
     * This method will be invoked when an uncaught processing error occurs.
     *
     * By default, compilation will fail when an error occurs. To change this behavior,
     * override this method and handle as you see fit.
     *
     * @param node the node during whose processing the error occurred
     * @param error the error that was caught
     *
     * @return Only return a node here if you wish to retry processing for this node. This will only work for
     *      nodes that are annotated, as they must be of type KSAnnotated
     */
    protected open fun onError(node: KSNode?, error: Throwable?): KSAnnotated? {
        node?.let {
            logger.error("Error processing ${it.location}:  $error", node)
        }
        // On failure, we should return this node to retry in the next round of processing
        return node as? KSAnnotated
    }

    /**
     * Given the [fileData] produced by processing the given [node], write the [fileData] to the proper file.
     *
     * Override this method to handle your own file writing logic.
     *
     * @param node the [KSNode] whose processing produced [fileData].
     * @param fileData the file data produced by processing [KSNode].
     */
    protected open suspend fun writeToFile(node: KSNode, fileData: GeneratedFile) {
        codeGenerator.createNewFile(
            fileData.dependencies ?: Dependencies(false, node.containingFile!!),
            fileData.packageName,
            fileData.fileName
        ).bufferedWriter().use {
            it.write(fileData.output)
        }
    }

    private suspend fun processNode(
        node: KSNode,
        processor: Processor<KSNode>
    ) = coroutineScope {
        processor.process(node)
            .forEach { fileData ->
                launch {
                    writeToFile(node, fileData)
                }
            }
    }
}