package test.stratify.strategies

import com.google.common.truth.Truth
import io.github.mattshoe.shoebox.test.buildCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test
import test.stratify.processors.FileNameProcessor
import test.stratify.processors.FileNameProcessorProvider

@OptIn(ExperimentalCompilerApi::class)
class FileNameStrategyTest {

    @Test
    fun `test processor runs when file name matches exactly`() = buildCompilation {
        processors(FileNameProcessorProvider())
        file(FileNameProcessor.FILE_NAME_MATCHER) {
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
            val generatedClassDoc = compilation.generatedFiles.firstOrNull { it.name == "iogithubmattshoetestTestkt_DocReader.kt" }
            Truth.assertThat(compilation.generatedFiles).hasSize(1)
            Truth.assertThat(generatedClassDoc).isNotNull()
        }
    }

    @Test
    fun `test processor does not generate file when file name does not match`() = buildCompilation {
        processors(FileNameProcessorProvider())
        file("NonMatchingTest.kt") {
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
            Truth.assertThat(compilation.generatedFiles).isEmpty()
        }
    }
}