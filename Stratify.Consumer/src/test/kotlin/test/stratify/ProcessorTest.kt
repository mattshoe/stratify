package test.stratify

import com.google.common.truth.Truth
import com.tschuchort.compiletesting.KotlinCompilation
import io.github.mattshoe.shoebox.test.buildCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test

@OptIn(ExperimentalCompilerApi::class)
class ProcessorTest {

    @Test
    fun `test processor generates expected files`() = buildCompilation {
        processors(AnnotationProcessorProvider())
        file("Test.kt") {
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
            val generatedClassDoc = compilation.generatedFiles.firstOrNull { it.name == "SomeInterface_DocReader.kt" }
            val generatedFunctionDoc = compilation.generatedFiles.firstOrNull { it.name == "iogithubmattshoetestfetchData_DocReader.kt" }
            Truth.assertThat(compilation.generatedFiles).hasSize(2)
            Truth.assertThat(generatedClassDoc).isNotNull()
            Truth.assertThat(generatedFunctionDoc).isNotNull()
        }
    }

    @Test
    fun `WHEN non-main dispatcher accesses resolver, compilation fails`() = buildCompilation {
        processors(NonMainResolverAccessProcessorProvider())
        file("Test.kt") {
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

        compile(KotlinCompilation.ExitCode.COMPILATION_ERROR)
    }

    @Test
    fun `WHEN non-main dispatcher accesses code generator, compilation fails`() = buildCompilation {
        processors(NonMainCodeGeneratorAccessProcessorProvider())
        file("Test.kt") {
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

        compile(KotlinCompilation.ExitCode.COMPILATION_ERROR)
    }

}