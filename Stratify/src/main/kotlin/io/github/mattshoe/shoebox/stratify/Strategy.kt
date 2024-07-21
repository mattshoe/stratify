package io.github.mattshoe.shoebox.stratify

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSNode

interface Strategy<T: KSNode> {
    val processors: List<Processor<T>>
    fun resolveNodes(resolver: Resolver, processor: Processor<T>): List<T>
}