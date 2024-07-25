package io.github.mattshoe.shoebox.stratify.util

import io.github.mattshoe.shoebox.stratify.dispatchers.StratifyMainHandler

internal fun ensureOnMainThread(location: String) {
    if (Thread.currentThread() != StratifyMainHandler.mainThread)
        throw IllegalAccessException("$location can only be accessed while on StratifyDispatcher.Main!")
}