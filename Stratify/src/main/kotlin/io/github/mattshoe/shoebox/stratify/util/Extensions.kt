package io.github.mattshoe.shoebox.stratify.util

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFile
import io.github.mattshoe.shoebox.stratify.dispatchers.StratifyMainHandler

internal fun ensureOnMainThread(location: String) {
    if (Thread.currentThread() != StratifyMainHandler.mainThread)
        throw IllegalAccessException("$location can only be accessed while on StratifyDispatcher.Main!")
}

fun Resolver.getSourceFiles(): Set<KSFile> {
    val newFiles = getNewFiles().toSet()
    val allFiles = getAllFiles().toSet()

    return if (allFiles == newFiles)
        allFiles
    else
        allFiles - newFiles
}