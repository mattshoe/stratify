package io.github.mattshoe.shoebox.stratify.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

internal object StratifyMainHandler {
    internal lateinit var mainThread: Thread

    val dispatcher: CoroutineDispatcher = mainDispatcherFactory()
}
