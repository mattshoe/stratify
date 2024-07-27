package io.github.mattshoe.shoebox.stratify.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

expect fun mainDispatcherFactory(): CoroutineDispatcher