package test.stratify

import com.tschuchort.compiletesting.KotlinCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.File

/**
 * A data class that contains ksp processed result.
 */
@OptIn(ExperimentalCompilerApi::class)
data class KspCompileResult constructor(
    val result: KotlinCompilation.Result,
    val generatedFiles: List<File>
)