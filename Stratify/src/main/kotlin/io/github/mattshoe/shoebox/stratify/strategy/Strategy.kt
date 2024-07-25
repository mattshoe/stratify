package io.github.mattshoe.shoebox.stratify.strategy

import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.kspwrappers.StratifyResolver
import io.github.mattshoe.shoebox.stratify.processor.Processor

interface Strategy<out TFilter: KSNode, TProcessor: TFilter> {
    val processors: List<Processor<TProcessor>>
    suspend fun resolveNodes(resolver: StratifyResolver, processor: Processor<KSNode>): List<TFilter>
}
