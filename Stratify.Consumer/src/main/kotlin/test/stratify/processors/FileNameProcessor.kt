package test.stratify.processors

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import io.github.mattshoe.shoebox.stratify.StratifySymbolProcessor
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.strategy.FileNameStrategy
import io.github.mattshoe.shoebox.stratify.strategy.FilePatternStrategy
import io.github.mattshoe.shoebox.stratify.stratifyProvider

class FileNameProcessorProvider: SymbolProcessorProvider by stratifyProvider<FileNameProcessor>()

/**
 * This is a sample Kotlin Symbol Processor to demonstrate usage of Stratify.
 *
 * It is just a simple processor that processes a dummy FileName called [DocReader].
 * The processor will do 2 things:
 *  1. Create an extension function for each class annotated with `@DocReader`,
 *     which will return the KDoc of the annotated class.
 *  2. Create an extension function for each method that is annotated with `@DocReader`,
 *     which will return the KDoc of the annotated method.
 */
class FileNameProcessor: StratifySymbolProcessor() {
    companion object {
        const val FILE_NAME_MATCHER = "Test.kt"
    }
    override suspend fun buildStrategies(resolver: StratifyResolver) = listOf(
        FilePatternStrategy(
            FILE_NAME_MATCHER,
            DocReaderFileProcessor()
        )
    )
}

