package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import io.github.mattshoe.shoebox.stratify.processor.Processor
import kotlin.reflect.KClass

/**
 * Defines a [Strategy] whose [processors] will receive all instances of
 * any [KSAnnotated] which is annotated by the specified [annotation].
 */
data class AnnotationStrategy(
    val annotation: KClass<out Annotation>,
    override val processors: List<Processor<KSAnnotated>>
): Strategy<KSAnnotated> {
    constructor(annotation: KClass<out Annotation>, vararg processors: Processor<KSAnnotated>): this(annotation, processors.toList())

    override fun resolveNodes(resolver: Resolver, processor: Processor<KSAnnotated>): List<KSAnnotated> {
        return resolver
            .getSymbolsWithAnnotation(annotation.qualifiedName!!)
            .filterIsInstance(processor.targetClass.java)
            .toList()

    }
}
