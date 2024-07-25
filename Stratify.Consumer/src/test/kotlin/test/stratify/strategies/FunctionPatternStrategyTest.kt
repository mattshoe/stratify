package test.stratify.strategies

import com.google.common.truth.Truth
import io.github.mattshoe.shoebox.test.buildCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test
import test.stratify.processors.FunctionPatternProcessor
import test.stratify.processors.FunctionPatternProcessorProvider

@OptIn(ExperimentalCompilerApi::class)
class FunctionPatternStrategyTest {

    @Test
    fun `test processor runs when file name matches`() = buildCompilation {
        processors(FunctionPatternProcessorProvider())
        file("ThisOneHasTestInItsName.kt") {
            """
                package io.github.mattshoe.test

                import test.stratify.annotation.DocReader

                /**
                 * This is SomeInterface that fetches some data.
                 */
                @DocReader
                interface SomeInterface {

                    /**
                     * This is a function that fetches some data
                     */
                    @DocReader
                    suspend fun thisMethodHasDataInItsName(param: String): String
                }

            """.trimIndent()
        }

        compile { compilation ->
            val generatedClassDoc = compilation.generatedFiles.firstOrNull { it.name == "iogithubmattshoetestthisMethodHasDataInItsName_DocReader.kt" }
            Truth.assertThat(compilation.generatedFiles).hasSize(1)
            Truth.assertThat(generatedClassDoc).isNotNull()
        }
    }

    @Test
    fun `test processor does not generate file when file name does not match`() = buildCompilation {
        processors(FunctionPatternProcessorProvider())
        file("NonMatching.kt") {
            """
                package io.github.mattshoe.test

                import test.stratify.annotation.DocReader

                /**
                 * This is SomeInterface that fetches some data.
                 */
                @DocReader
                interface SomeInterface {

                    /**
                     * This is a function that fetches some data
                     */
                    @DocReader
                    suspend fun thisDoesNotMatchThePattern(param: String): String
                }

            """.trimIndent()
        }

        compile { compilation ->
            Truth.assertThat(compilation.generatedFiles).isEmpty()
        }
    }
}