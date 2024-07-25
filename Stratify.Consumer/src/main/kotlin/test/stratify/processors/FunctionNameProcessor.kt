package test.stratify.processors

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import io.github.mattshoe.shoebox.stratify.StratifySymbolProcessor
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.strategy.FunctionNameStrategy
import io.github.mattshoe.shoebox.stratify.stratifyProvider

class FunctionNameProcessorProvider: SymbolProcessorProvider by stratifyProvider<FunctionNameProcessor>()

/**
 * This is a sample Kotlin Symbol Processor to demonstrate usage of Stratify.
 *
 * It is just a simple processor that processes a dummy FunctionName called [DocReader].
 * The processor will do 2 things:
 *  1. Create an extension function for each class annotated with `@DocReader`,
 *     which will return the KDoc of the annotated class.
 *  2. Create an extension function for each method that is annotated with `@DocReader`,
 *     which will return the KDoc of the annotated method.
 */
class FunctionNameProcessor: StratifySymbolProcessor() {
    companion object {
        const val FUNCTION_NAME_MATCHER = "io.github.mattshoe.test.SomeInterface.fetchData"
    }
    override suspend fun buildStrategies(resolver: StratifyResolver) = listOf(
        FunctionNameStrategy(
            FUNCTION_NAME_MATCHER,
            DocReaderFunctionProcessor()
        )
    )
}

