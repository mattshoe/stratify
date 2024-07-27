package io.github.mattshoe.shoebox.stratify.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory

internal object StratifyMainHandler {
    // Create a blocking queue to hold Runnable tasks
    private val taskQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()

    // Create our Main thread, which just continuously pops the task queue and executes tasks.
    val mainThread = Thread(
        {
            try {
                while (!Thread.currentThread().isInterrupted) {
                    // Take and execute tasks from the queue
                    val task = taskQueue.take()
                    task.run()
                }
            } catch (e: InterruptedException) {
                // Handle thread interruption
                Thread.currentThread().interrupt()
            }
        },
        "StratifyMain"
    )

    // Create our Main thread factory which just enqueues the given runnable and returns the Main thread
    private val mainThreadFactory = ThreadFactory { runnable ->
        mainThread.also {
            taskQueue.put(runnable)
        }
    }

    val dispatcher: CoroutineDispatcher = Executors.newSingleThreadExecutor(mainThreadFactory).asCoroutineDispatcher()
}