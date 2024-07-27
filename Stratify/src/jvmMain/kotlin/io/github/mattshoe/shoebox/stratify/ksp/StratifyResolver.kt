package io.github.mattshoe.shoebox.stratify.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFile
import io.github.mattshoe.shoebox.stratify.dispatchers.StratifyDispatcher
import kotlinx.coroutines.withContext

class StratifyResolver(private val resolver: Resolver) {

    /**
     * Use this method to operate on the KSP [Resolver] instance for this symbol processor.
     * This method ensures that all access to the [Resolver] is done on the main thread, as the [Resolver]
     * implementation is not inherently thread-safe.
     */
    suspend fun <T> use(action: Resolver.() -> T): T = withContext(StratifyDispatcher.Main) {
        resolver.action()
    }
}