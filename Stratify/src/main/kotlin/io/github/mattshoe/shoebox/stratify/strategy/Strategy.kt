package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.processor.Processor

interface Strategy<out T: KSNode> {
    val processors: List<Processor<T>>
    fun resolveNodes(resolver: Resolver, processor: Processor<@UnsafeVariance T>): List<T>
}