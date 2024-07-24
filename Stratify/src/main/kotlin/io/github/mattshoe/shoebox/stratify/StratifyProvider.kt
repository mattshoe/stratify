package io.github.mattshoe.shoebox.stratify

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import io.github.mattshoe.shoebox.stratify.model.StratifySymbolProcessorEnvironment

lateinit var stratifySymbolProcessorEnvironment: StratifySymbolProcessorEnvironment

inline fun <reified T: StratifySymbolProcessor> stratifyProvider(): SymbolProcessorProvider {
    return SymbolProcessorProvider { environment ->
        try {
            stratifySymbolProcessorEnvironment = StratifySymbolProcessorEnvironment(environment)
            T::class.java.getDeclaredConstructor().newInstance()
        } catch (e: Throwable) {
            val message = "${T::class} must have a no-arg constructor!"
            environment.logger.error(message)
            throw IllegalStateException(message, e)
        }

    }
}