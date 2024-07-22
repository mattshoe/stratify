package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFile
import io.github.mattshoe.shoebox.stratify.processor.Processor

/**
 * Defines a [Strategy] whose [processors] will receive all instances of any [KSFile] whose name
 * exactly matches the given [name].
 */
//data class FileNameStrategy(
//    val name: String,
//    override val processors: List<Processor<KSFile>>
//): Strategy<KSFile> {
//    constructor(name: String, vararg processors: Processor<KSFile>): this(name, processors.toList())
//
//    override fun resolveNodes(resolver: Resolver, processor: Processor<KSFile>): List<KSFile> {
//        return resolver
//            .getAllFiles()
//            .filter {
//                it.fileName == name
//            }.toList()
//    }
//}