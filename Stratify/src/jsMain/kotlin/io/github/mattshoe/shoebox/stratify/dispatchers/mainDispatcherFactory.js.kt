package io.github.mattshoe.shoebox.stratify.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext
import kotlinx.browser.window

actual fun mainDispatcherFactory(): CoroutineDispatcher = SingleThreadDispatcher()

class SingleThreadDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        window.setTimeout({ block.run() }, 0)
    }
}
