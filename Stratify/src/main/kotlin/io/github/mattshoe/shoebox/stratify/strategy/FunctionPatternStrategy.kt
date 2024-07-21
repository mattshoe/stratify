package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import io.github.mattshoe.shoebox.stratify.processor.Processor

/**
 * Defines a [Strategy] whose [processors] will receive all instances of [KSFunctionDeclaration]
 * whose fully qualified name matches the given [pattern].
 *
 * @param pattern regex to match function names.
 */
data class FunctionPatternStrategy(
    val pattern: String,
    override val processors: List<Processor<KSFunctionDeclaration>>
): Strategy<KSFunctionDeclaration> {
    constructor(name: String, vararg processors: Processor<KSFunctionDeclaration>): this(name, processors.toList())

    private val regex = Regex(pattern)

    override fun resolveNodes(
        resolver: Resolver,
        processor: Processor<KSFunctionDeclaration>
    ): List<KSFunctionDeclaration> {
        return resolver
            .getAllFiles()
            .flatMap {
                it.declarations.filterIsInstance<KSFunctionDeclaration>()
            }
            .filter {
                it.qualifiedName?.asString()?.matches(regex) ?: false
            }
            .toList()
    }
}

