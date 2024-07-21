package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
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
): Strategy<KSPropertyDeclaration> {
    constructor(name: String, vararg processors: Processor<KSPropertyDeclaration>): this(name, processors.toList())

    override fun resolveNodes(
        resolver: Resolver,
        processor: Processor<KSPropertyDeclaration>
    ): List<KSPropertyDeclaration> {
        return resolver
            .getAllFiles()
            .flatMap {
                it.declarations.filterIsInstance<KSPropertyDeclaration>()
            }
            .filter {
                it.qualifiedName?.asString() == name
            }
            .toList()
    }
}