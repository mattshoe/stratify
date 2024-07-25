package io.github.mattshoe.shoebox.test

import com.google.common.truth.Truth
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspIncremental
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
class KspCompilationBuilder {
    internal val processors = mutableListOf<SymbolProcessorProvider>()
    internal val sourceFiles = mutableListOf<SourceFile>()

    private val configuration = KotlinCompilation().apply {
        inheritClassPath = true
        verbose = true
        kspIncremental = false
    }

    fun processors(vararg providers: SymbolProcessorProvider) {
        processors.addAll(providers)
    }

    fun options(action: KotlinCompilation.() -> Unit) {
        configuration.action()
    }

    fun file(name: String, content: () -> String) {
        sourceFiles.add(
            SourceFile.kotlin(
                name,
                content(),
                true,
                false
            )
        )
    }

    fun compile(
        expectedExitCode: KotlinCompilation.ExitCode = KotlinCompilation.ExitCode.OK,
        action: (KspCompilation) -> Unit = {}
    ) {
        val compilation = configuration.apply {
            symbolProcessorProviders = processors
            sources = sourceFiles
        }
        val result = compilation.compile()
        Truth.assertThat(result.exitCode).isEqualTo(expectedExitCode)
        action(
            KspCompilation(
                result,
                findGeneratedFiles(compilation)
            )
        )
    }

}