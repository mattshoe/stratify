package io.github.mattshoe.shoebox.stratify

import com.google.devtools.ksp.symbol.KSNode
import kotlin.reflect.KClass

/**
 * Defines a Processor meant to handle a specific subclass of [KSNode].
 */
interface Processor<out T: KSNode> {
    /**
     * This is just the [KClass] of [T] which you specified for the class.
     *
     * This is necessary due to the limitation imposed by Type Erasure in Java.
     */
    val targetClass: KClass<@UnsafeVariance T>

    /**
     * Given an instance of the [targetClass], performs the processing of that declaration
     * and returns a set of [GeneratedFile] that can be written to the project.
     */
    suspend fun process(node: @UnsafeVariance T): Set<GeneratedFile>
}
