package test.stratify.strategies

import com.google.common.truth.Truth
import io.github.mattshoe.shoebox.test.buildCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test
import test.stratify.processors.FunctionNameProcessor
import test.stratify.processors.FunctionNameProcessorProvider

@OptIn(ExperimentalCompilerApi::class)
class FunctionNameStrategyTest {

    @Test
    fun `test processor runs when file name matches exactly`() = buildCompilation {
        processors(FunctionNameProcessorProvider())
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
                    suspend fun fetchData(param: String): String
                }

            """.trimIndent()
        }

        compile { compilation ->
            val generatedClassDoc = compilation.generatedFiles.firstOrNull { it.name == "iogithubmattshoetestfetchData_DocReader.kt" }
            Truth.assertThat(compilation.generatedFiles).hasSize(1)
            Truth.assertThat(generatedClassDoc).isNotNull()
        }
    }

    @Test
    fun `test processor does not generate file when file name does not match`() = buildCompilation {
        processors(FunctionNameProcessorProvider())
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
                    suspend fun fetchData1(param: String): String
                }

            """.trimIndent()
        }

        compile { compilation ->
            Truth.assertThat(compilation.generatedFiles).isEmpty()
        }
    }
}