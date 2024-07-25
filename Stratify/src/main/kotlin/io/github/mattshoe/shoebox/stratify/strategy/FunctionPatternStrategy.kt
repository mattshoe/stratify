package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.kspwrappers.StratifyResolver
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
): Strategy<KSFunctionDeclaration, KSFunctionDeclaration> {
    constructor(name: String, vararg processors: Processor<KSFunctionDeclaration>): this(name, processors.toList())

    private val regex = Regex(pattern)

    override suspend fun resolveNodes(
        resolver: StratifyResolver,
        processor: Processor<KSNode>
    ): List<KSFunctionDeclaration> {
        return resolver.use {
            getAllFiles()
        }.flatMap {
            it.declarations.filterIsInstance<KSFunctionDeclaration>()
        }.filter {
            it.qualifiedName?.asString()?.matches(regex) ?: false
        }.toList()
    }
}

