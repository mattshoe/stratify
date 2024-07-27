package io.github.mattshoe.shoebox.stratify.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import io.github.mattshoe.shoebox.stratify.dispatchers.StratifyDispatcher
import kotlinx.coroutines.withContext

class StratifyCodeGenerator(
    private val codeGenerator: CodeGenerator
) {
    suspend fun <T> use(action: CodeGenerator.() -> T): T = withContext(StratifyDispatcher.Main) {
        return@withContext codeGenerator.action()
    }
}