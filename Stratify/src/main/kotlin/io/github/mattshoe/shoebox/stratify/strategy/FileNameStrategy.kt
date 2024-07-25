package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.processor.Processor

/**
 * Defines a [Strategy] whose [processors] will receive all instances of any [KSFile] whose name
 * exactly matches the given [name].
 */
data class FileNameStrategy(
    val name: String,
    override val processors: List<Processor<KSFile>>
): Strategy<KSFile, KSFile> {
    constructor(name: String, vararg processors: Processor<KSFile>): this(name, processors.toList())

    override suspend fun resolveNodes(resolver: StratifyResolver, processor: Processor<KSNode>): List<KSFile> {
        return resolver.use {
            getAllFiles()
        }.filter {
            it.fileName == name
        }.toList()
    }
}