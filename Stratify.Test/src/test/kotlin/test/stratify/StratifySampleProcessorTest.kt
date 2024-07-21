package test.stratify

import com.google.common.truth.Truth
import com.tschuchort.compiletesting.*
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

@OptIn(ExperimentalCompilerApi::class)
class StratifySampleProcessorTest {

    @Test
    fun `test processor generates expected files`() {
        assertOutput(
            classDocName = "SomeInterface_DocReader.kt",
            functionDocName = "fetchData_DocReader.kt",
            sourceContent =  """
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

            """.trimIndent(),
            expectedClassDocOutput = """
                package io.github.mattshoe.test

                import kotlin.String
                
                public fun SomeInterface.readDoc(): String = ""${'"'}
                |
                | This is SomeInterface that fetches some data.
                |""${'"'}.trimMargin()
            """.trimIndent(),
            expectedFunctionDocOutput = """
                package io.github.mattshoe.test
                
                import kotlin.String
                
                public fun SomeInterface.readDocForFetchData(): String = ""${'"'}
                |
                | This is a function that fetches some data
                |""${'"'}.trimMargin()
            """.trimIndent()
        )
    }

    private fun assertOutput(
        classDocName: String,
        functionDocName: String,
        sourceContent: String,
        expectedClassDocOutput: String,
        expectedFunctionDocOutput: String,
        exitCode: KotlinCompilation.ExitCode = KotlinCompilation.ExitCode.OK
    ) {
        val kspCompileResult = compile(
            SourceFile.kotlin(
                "Test.kt",
                sourceContent
            )
        )

        Truth.assertThat(kspCompileResult.result.exitCode).isEqualTo(exitCode)

        val generatedClassDoc = kspCompileResult.generatedFiles.first { it.name == classDocName }
        val generatedFunctionDoc = kspCompileResult.generatedFiles.first { it.name == functionDocName }

        Truth.assertThat(generatedClassDoc).isNotNull()
        assertEquals(expectedClassDocOutput.trimIndent(), generatedClassDoc.readText().trimIndent())

        Truth.assertThat(generatedFunctionDoc).isNotNull()
        assertEquals(expectedFunctionDocOutput.trimIndent(), generatedFunctionDoc.readText().trimIndent())
    }

    private fun compile(vararg sourceFiles: SourceFile): KspCompileResult {
        val compilation = prepareCompilation(*sourceFiles)
        val result = compilation.compile()
        return KspCompileResult(
            result,
            findGeneratedFiles(compilation)
        )
    }

    private fun prepareCompilation(vararg sourceFiles: SourceFile): KotlinCompilation =
        KotlinCompilation()
            .apply {
                inheritClassPath = true
                symbolProcessorProviders = listOf(StratifySampleProcessorProvider())
                sources = sourceFiles.asList()
                verbose = true
                kspIncremental = false
            }

    private fun findGeneratedFiles(compilation: KotlinCompilation): List<File> {
        return compilation.kspSourcesDir
            .walkTopDown()
            .filter { it.isFile }
            .toList()
    }
}