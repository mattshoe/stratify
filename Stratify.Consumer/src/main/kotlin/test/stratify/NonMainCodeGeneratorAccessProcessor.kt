package test.stratify

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSNode
import io.github.mattshoe.shoebox.stratify.StratifySymbolProcessor
import io.github.mattshoe.shoebox.stratify.ksp.StratifyResolver
import io.github.mattshoe.shoebox.stratify.strategy.Strategy
import io.github.mattshoe.shoebox.stratify.stratifyProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NonMainCodeGeneratorAccessProcessorProvider: SymbolProcessorProvider by stratifyProvider<NonMainCodeGeneratorAccessProcessor>()

class NonMainCodeGeneratorAccessProcessor: StratifySymbolProcessor() {
    override suspend fun buildStrategies(resolver: StratifyResolver): List<Strategy<KSNode, out KSNode>> {
        var kspCodeGenerator: CodeGenerator? = null
        codeGenerator.use {
            kspCodeGenerator = this
        }

        withContext(Dispatchers.IO) {
            kspCodeGenerator?.generatedFile
        }

        return emptyList()

    }
}