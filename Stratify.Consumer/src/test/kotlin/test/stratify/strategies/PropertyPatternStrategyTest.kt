package test.stratify.strategies

import com.google.common.truth.Truth
import io.github.mattshoe.shoebox.test.buildCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test
import test.stratify.processors.PropertyPatternProcessor
import test.stratify.processors.PropertyPatternProcessorProvider

@OptIn(ExperimentalCompilerApi::class)
class PropertyPatternStrategyTest {

    @Test
    fun `test processor runs when file name matches`() = buildCompilation {
        processors(PropertyPatternProcessorProvider())
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
                     * This is a property that  has some state
                     */
                    @DocReader
                    val thisOneHasStateInTheName: Int

                    /**
                     * This is a function that fetches some data
                     */
                    @DocReader
                    suspend fun thisMethodHasDataInItsName(param: String): String
                }

            """.trimIndent()
        }

        compile { compilation ->
            val generatedClassDoc = compilation.generatedFiles.firstOrNull { it.name == "iogithubmattshoetestthisOneHasStateInTheName_DocReader.kt" }
            Truth.assertThat(compilation.generatedFiles).hasSize(1)
            Truth.assertThat(generatedClassDoc).isNotNull()
        }
    }

    @Test
    fun `test processor does not generate file when file name does not match`() = buildCompilation {
        processors(PropertyPatternProcessorProvider())
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
                     * This is a property that has some state
                     */
                    @DocReader
                    val thisOneDoesNotMatch: Int

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