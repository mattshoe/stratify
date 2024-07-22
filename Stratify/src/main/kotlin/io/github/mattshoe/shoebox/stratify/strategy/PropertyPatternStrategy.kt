package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import io.github.mattshoe.shoebox.stratify.processor.Processor

/**
 * Defines a [Strategy] whose [processors] will receive all instances of [KSPropertyDeclaration]
 * whose fully qualified name matches the given [name].
 *
 * @param pattern regex to match property names.
 */
//data class PropertyPatternStrategy(
//    val pattern: String,
//    override val processors: List<Processor<KSPropertyDeclaration>>
//): Strategy<KSPropertyDeclaration> {
//    constructor(name: String, vararg processors: Processor<KSPropertyDeclaration>): this(name, processors.toList())
//
//    private val regex = Regex(pattern)
//
//    override fun resolveNodes(
//        resolver: Resolver,
//        processor: Processor<KSPropertyDeclaration>
//    ): List<KSPropertyDeclaration> {
//        return resolver
//            .getAllFiles()
//            .flatMap {
//                it.declarations.filterIsInstance<KSPropertyDeclaration>()
//            }
//            .filter {
//                it.qualifiedName?.asString()?.matches(regex) ?: false
//            }
//            .toList()
//    }
//}