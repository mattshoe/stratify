package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFile
import io.github.mattshoe.shoebox.stratify.processor.Processor

/**
 * Defines a [Strategy] whose [processors] will receive all new [KSFile] instances.
 *
 * The term "new" here is as defined by [Resolver.getNewFiles].
 *
 * @see [Resolver.getNewFiles]
 */
data class NewFilesStrategy(
    override val processors: List<Processor<KSFile>>
): Strategy<KSFile> {
    constructor(vararg processors: Processor<KSFile>): this(processors.toList())

    override fun resolveNodes(resolver: Resolver, processor: Processor<KSFile>): List<KSFile> {
        return resolver.getNewFiles().toList()
    }
}