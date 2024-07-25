package io.github.mattshoe.shoebox.test

import com.tschuchort.compiletesting.KotlinCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.File

/**
 * A data class that contains ksp processed result.
 */
@OptIn(ExperimentalCompilerApi::class)
data class KspCompilation constructor(
    val result: KotlinCompilation.Result,
    val generatedFiles: List<File>
)