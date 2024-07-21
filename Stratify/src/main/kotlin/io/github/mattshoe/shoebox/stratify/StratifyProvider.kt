package io.github.mattshoe.shoebox.stratify

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

inline fun <reified T: StratifySymbolProcessor> stratifyProvider(): SymbolProcessorProvider {
    return SymbolProcessorProvider { environment ->
        try {
            T::class.java.getDeclaredConstructor(environment::class.java).newInstance(environment)
        } catch (e: Throwable) {
            val message = "Constructor for ${T::class} must have only one parameter, and it must be of type ${SymbolProcessorEnvironment::class.simpleName}"
            environment.logger.error(message)
            throw IllegalStateException(message, e)
        }

    }
}