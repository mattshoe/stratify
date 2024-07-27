package io.github.mattshoe.shoebox.stratify.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

object StratifyDispatcher {




    /**
     * You MUST use this dispatcher while accessing any KSP APIs. KSP is not thread-safe by
     * default, so you must ensure that any operations you invoke on any KSP API runs on the
     * Main thread.
     *
     * You do not need to use [StratifyDispatcher.Main] for processing and running your own code,
     * you are only responsible for making sure any KSP APIs are invoked on [StratifyDispatcher.Main].
     */
    val Main: CoroutineDispatcher = StratifyMainHandler.dispatcher
}

