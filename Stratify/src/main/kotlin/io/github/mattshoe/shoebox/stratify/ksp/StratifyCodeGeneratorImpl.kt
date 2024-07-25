package io.github.mattshoe.shoebox.stratify.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import io.github.mattshoe.shoebox.stratify.util.ensureOnMainThread
import java.io.File
import java.io.OutputStream

internal class StratifyCodeGeneratorImpl(
    private val codeGenerator: CodeGenerator
): CodeGenerator {

    private val errorMessage = "You cannot use `SymbolProcessorEnvironment.codeGenerator`, you must use `StratifySymbolProcessor.codeGenerator` instead."

    override val generatedFile: Collection<File>
        get() = run {
            ensureMain()
            codeGenerator.generatedFile
        }

    override fun associate(sources: List<KSFile>, packageName: String, fileName: String, extensionName: String) {
        ensureMain()
        codeGenerator.associate(sources, packageName, fileName, extensionName)
    }

    override fun associateByPath(sources: List<KSFile>, path: String, extensionName: String) {
        ensureMain()
        codeGenerator.associateByPath(sources, path, extensionName)
    }

    override fun associateWithClasses(
        classes: List<KSClassDeclaration>,
        packageName: String,
        fileName: String,
        extensionName: String
    ) {
        ensureMain()
        codeGenerator.associateWithClasses(
            classes,
            packageName,
            fileName,
            extensionName
        )
    }

    override fun createNewFile(
        dependencies: Dependencies,
        packageName: String,
        fileName: String,
        extensionName: String
    ): OutputStream {
        ensureMain()
        return codeGenerator.createNewFile(
            dependencies,
            packageName,
            fileName,
            extensionName
        )
    }

    override fun createNewFileByPath(dependencies: Dependencies, path: String, extensionName: String): OutputStream {
        ensureMain()
        return codeGenerator.createNewFileByPath(dependencies, path, extensionName)
    }
    
    private fun ensureMain() {
        ensureOnMainThread(CodeGenerator::class.simpleName.toString())
    }

}