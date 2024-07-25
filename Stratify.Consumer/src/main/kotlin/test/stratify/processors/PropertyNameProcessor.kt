package test.stratify.processors

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import io.github.mattshoe.shoebox.stratify.StratifySymbolProcessor
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.strategy.PropertyNameStrategy
import io.github.mattshoe.shoebox.stratify.stratifyProvider

class PropertyNameProcessorProvider: SymbolProcessorProvider by stratifyProvider<PropertyNameProcessor>()

/**
 * This is a sample Kotlin Symbol Processor to demonstrate usage of Stratify.
 *
 * It is just a simple processor that processes a dummy PropertyName called [DocReader].
 * The processor will do 2 things:
 *  1. Create an extension function for each class annotated with `@DocReader`,
 *     which will return the KDoc of the annotated class.
 *  2. Create an extension function for each method that is annotated with `@DocReader`,
 *     which will return the KDoc of the annotated method.
 */
class PropertyNameProcessor: StratifySymbolProcessor() {
    companion object {
        const val PROPERTY_NAME_MATCHER = "state"
    }
    override suspend fun buildStrategies(resolver: StratifyResolver) = listOf(
        PropertyNameStrategy(
            PROPERTY_NAME_MATCHER,
            DocReaderPropertyProcessor()
        )
    )
}

