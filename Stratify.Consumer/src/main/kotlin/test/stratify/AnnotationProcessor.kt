package test.stratify

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import io.github.mattshoe.shoebox.stratify.StratifySymbolProcessor
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.strategy.AnnotationStrategy
import io.github.mattshoe.shoebox.stratify.stratifyProvider
import test.stratify.annotation.DocReader
import test.stratify.processors.DocReaderClassProcessor
import test.stratify.processors.DocReaderFunctionProcessor

class AnnotationProcessorProvider: SymbolProcessorProvider by stratifyProvider<AnnotationProcessor>()

/**
 * This is a sample Kotlin Symbol Processor to demonstrate usage of Stratify.
 *
 * It is just a simple processor that processes a dummy annotation called [DocReader].
 * The processor will do 2 things:
 *  1. Create an extension function for each class annotated with `@DocReader`,
 *     which will return the KDoc of the annotated class.
 *  2. Create an extension function for each method that is annotated with `@DocReader`,
 *     which will return the KDoc of the annotated method.
 */
class AnnotationProcessor: StratifySymbolProcessor() {
    override suspend fun buildStrategies(resolver: StratifyResolver) = listOf(
        AnnotationStrategy(
            annotation = DocReader::class,
            DocReaderClassProcessor(),
            DocReaderFunctionProcessor()
        )
    )
}

