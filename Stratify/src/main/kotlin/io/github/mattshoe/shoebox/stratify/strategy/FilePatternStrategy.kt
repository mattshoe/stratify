package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.processor.Processor

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

    private val regex = Regex(pattern)

    override suspend fun resolveNodes(resolver: StratifyResolver, processor: Processor<KSNode>): List<KSFile> {
        return resolver.use {
            getAllFiles()
        }.filter {
            it::class.java.isAssignableFrom(processor.targetClass.java)
                    && it.fileName.matches(regex)
        }.toList()
    }
}