package test.stratify

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.StratifySymbolProcessor
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.strategy.Strategy
import io.github.mattshoe.shoebox.stratify.stratifyProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NonMainResolverAccessProcessorProvider: SymbolProcessorProvider by stratifyProvider<NonMainResolverAccessProcessor>()

class NonMainResolverAccessProcessor: StratifySymbolProcessor() {
    override suspend fun buildStrategies(resolver: StratifyResolver): List<Strategy<KSNode, out KSNode>> {
        var kspResolver: Resolver? = null
        resolver.use {
            kspResolver = this
        }

        withContext(Dispatchers.IO) {
            kspResolver?.builtIns?.intType
        }

        return emptyList()
    }
}

