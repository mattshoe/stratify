package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFile
import io.github.mattshoe.shoebox.stratify.processor.Processor

/**
 * Defines a [Strategy] whose [processors] will receive all [KSFile] instances whose file name
 * matches the specified [pattern].
 *
 * Note that [pattern] is a Regex.
 */
//data class FilePatternStrategy(
//    val pattern: String,
//    override val processors: List<Processor<KSFile>>
//): Strategy<KSFile> {
//    constructor(pattern: String, vararg processors: Processor<KSFile>): this(pattern, processors.toList())
//
//    private val regex = Regex(pattern)
//
//    override fun resolveNodes(resolver: Resolver, processor: Processor<KSFile>): List<KSFile> {
//        return resolver
//            .getAllFiles()
//            .filter {
//                it::class.java.isAssignableFrom(processor.targetClass.java)
//                    && it.fileName.matches(regex)
//            }
//            .toList()
//    }
//}