package io.github.mattshoe.shoebox.stratify

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSNode
import kotlin.reflect.KClass

data class AnnotationStrategy(
    val annotation: KClass<out Annotation>,
    override val processors: List<Processor<KSNode>>
): Strategy<KSNode> {

    constructor(annotation: KClass<out Annotation>, vararg processors: Processor<KSNode>): this(annotation, processors.toList())

    override fun resolveNodes(resolver: Resolver, processor: Processor<KSNode>): List<KSNode> {
        return resolver
            .getSymbolsWithAnnotation(annotation.qualifiedName!!)
            .filterIsInstance(processor.targetClass.java)
            .toList()

    }
}

