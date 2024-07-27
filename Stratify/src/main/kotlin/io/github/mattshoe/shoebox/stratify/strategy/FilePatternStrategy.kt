package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.Origin
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.processor.Processor
import io.github.mattshoe.shoebox.stratify.util.getSourceFiles
import kotlin.reflect.KClass

/**
 * Defines a [Strategy] whose [processors] will receive all [KSFile] instances whose file name
 * matches the specified [pattern].
 *
 * Note that [pattern] is a Regex.
 */
data class FilePatternStrategy(
    val pattern: String,
    override val processors: List<Processor<KSFile>>
): Strategy<KSFile, KSFile> {
    constructor(pattern: String, vararg processors: Processor<KSFile>): this(pattern, processors.toList())
    companion object {
        private val alreadyProcessed = mutableSetOf<String>()
    }

    private val regex = Regex(pattern)

    override suspend fun resolveNodes(resolver: StratifyResolver): List<KSFile> {
        return resolver.use {
            getSourceFiles()
        }.filter { file ->
            shouldProcess(file).also {
                alreadyProcessed.add(file.filePath)
            }
        }.toList()
    }

    private fun shouldProcess(file: KSFile): Boolean {
        return !alreadyProcessed.contains(file.filePath)
                && file.fileName.matches(regex)
    }


}