package io.github.mattshoe.shoebox.test

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.kspSourcesDir
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.File


fun buildCompilation(action: KspCompilationBuilder.() -> Unit) {
    KspCompilationBuilder().apply(action)
}


@OptIn(ExperimentalCompilerApi::class)
internal fun findGeneratedFiles(compilation: KotlinCompilation): List<File> {
    return compilation.kspSourcesDir
        .walkTopDown()
        .filter { it.isFile }
        .toList()
}