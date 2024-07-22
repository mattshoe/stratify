package test.stratify

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.strategy.AnnotationStrategy
import io.github.mattshoe.shoebox.stratify.StratifySymbolProcessor
import io.github.mattshoe.shoebox.stratify.strategy.Strategy
import test.stratify.annotation.DocReader
import test.stratify.processors.DocReaderClassProcessor
import test.stratify.processors.DocReaderFunctionProcessor

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
class StratifySampleProcessor(
    environment: SymbolProcessorEnvironment
) : StratifySymbolProcessor(
    environment
) {
    override val strategies = listOf(
        AnnotationStrategy(
            annotation = DocReader::class,
            DocReaderClassProcessor(),
            DocReaderFunctionProcessor()
        )
    )

    over
}
