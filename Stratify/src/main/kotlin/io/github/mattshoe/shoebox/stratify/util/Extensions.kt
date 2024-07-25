package io.github.mattshoe.shoebox.stratify.util

import io.github.mattshoe.shoebox.stratify.dispatchers.StratifyMainHandler

fun ensureOnMainThread() {
    if (Thread.currentThread() != StratifyMainHandler.mainThread)
        throw IllegalAccessException("CodeGenerator can only be accessed while on StratifyDispatcher.Main!")
}