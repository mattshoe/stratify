package io.github.mattshoe.shoebox.stratify.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_queue_create
import platform.darwin.dispatch_queue_t
import platform.darwin.dispatch_sync
import kotlin.coroutines.CoroutineContext

//actual fun mainDispatcherFactory(): CoroutineDispatcher = SingleThreadDispatcher("StratifyMain")
//
//class SingleThreadDispatcher(name: String) : CoroutineDispatcher() {
//    private val dispatchQueue: dispatch_queue_t =
//        dispatch_queue_create(name, null)
//
//    override fun dispatch(context: CoroutineContext, block: Runnable) {
//        dispatch_sync(dispatchQueue) {
//            block.run()
//        }
//    }
//}