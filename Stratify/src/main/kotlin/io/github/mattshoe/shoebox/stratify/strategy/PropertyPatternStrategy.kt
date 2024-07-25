package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.processor.Processor
import io.github.mattshoe.shoebox.stratify.util.getSourceFiles

/**
 * Defines a [Strategy] whose [processors] will receive all instances of [KSPropertyDeclaration]
 * whose fully qualified name matches the given [name].
 *
 * @param pattern regex to match property names.
 */
data class PropertyPatternStrategy(
    val pattern: String,
    override val processors: List<Processor<KSPropertyDeclaration>>
): Strategy<KSPropertyDeclaration, KSPropertyDeclaration> {
    constructor(name: String, vararg processors: Processor<KSPropertyDeclaration>): this(name, processors.toList())

    private val regex = Regex(pattern)

    override suspend fun resolveNodes(
        resolver: StratifyResolver,
        processor: Processor<KSNode>
    ): List<KSPropertyDeclaration> {
        return resolver.use {
            getSourceFiles()
        }.flatMap {
            it.declarations.filterIsInstance<KSPropertyDeclaration>()
        }.filter {
            it.qualifiedName?.asString()?.matches(regex) ?: false
        }.toList()
    }
}