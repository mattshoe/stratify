package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.getFunctionDeclarationsByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import io.github.mattshoe.shoebox.stratify.processor.Processor

/**
 * Defines a [Strategy] whose [processors] will receive all instances of [KSFunctionDeclaration]
 * whose fully qualified name matches [name].
 *
 * NOTE: This will not include top-level functions, as that is a very expensive operation.
 * You can implement your own [Strategy] if you need to include top-level functions.
 *
 * @param name fully qualified name of the function to be loaded; using '.' as separator.
 */
//data class FunctionNameStrategy(
//    val name: String,
//    override val processors: List<Processor<KSFunctionDeclaration>>
//): Strategy<KSFunctionDeclaration> {
//    constructor(name: String, vararg processors: Processor<KSFunctionDeclaration>): this(name, processors.toList())
//
//    override fun resolveNodes(
//        resolver: Resolver,
//        processor: Processor<KSFunctionDeclaration>
//    ): List<KSFunctionDeclaration> {
//        return resolver
//            .getFunctionDeclarationsByName(name)
//            .toList()
//    }
//}

