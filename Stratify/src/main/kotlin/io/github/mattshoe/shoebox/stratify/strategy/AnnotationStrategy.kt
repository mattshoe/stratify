package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.processor.Processor
import kotlin.reflect.KClass

/**
 * Defines a [Strategy] whose [processors] will receive all instances of
 * any [KSAnnotated] which is annotated by the specified [annotation].
 */
data class AnnotationStrategy(
    val annotation: KClass<out Annotation>,
    override val processors: List<Processor<KSAnnotated>>
): Strategy<KSAnnotated, KSAnnotated> {
    constructor(annotation: KClass<out Annotation>, vararg processors: Processor<KSAnnotated>): this(annotation, processors.toList())

    override suspend fun resolveNodes(resolver: StratifyResolver): List<KSAnnotated> {
        return resolver.use {
            getSymbolsWithAnnotation(annotation.qualifiedName ?: "")
        }.toList()
    }
}
