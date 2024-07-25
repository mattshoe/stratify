package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import io.github.mattshoe.shoebox.stratify.kspwrappers.StratifyResolver
import io.github.mattshoe.shoebox.stratify.processor.Processor

/**
 * Defines a [Strategy] whose [processors] will receive all instances of [KSPropertyDeclaration]
 * whose fully qualified name matches the given [name].
 *
 * @param name fully qualified name of the function to be loaded; using '.' as separator.
 */
data class PropertyNameStrategy(
    val name: String,
    override val processors: List<Processor<KSPropertyDeclaration>>
): Strategy<KSPropertyDeclaration, KSPropertyDeclaration> {
    constructor(name: String, vararg processors: Processor<KSPropertyDeclaration>): this(name, processors.toList())

    override suspend fun resolveNodes(
        resolver: StratifyResolver,
        processor: Processor<KSNode>
    ): List<KSPropertyDeclaration> {
        return resolver.use {
            getAllFiles()
        }.flatMap {
            it.declarations.filterIsInstance<KSPropertyDeclaration>()
        }.filter {
            it.qualifiedName?.asString() == name
        }.toList()
    }
}