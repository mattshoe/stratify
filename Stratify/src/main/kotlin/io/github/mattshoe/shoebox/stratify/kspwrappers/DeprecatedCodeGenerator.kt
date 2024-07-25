package io.github.mattshoe.shoebox.stratify.kspwrappers

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import java.io.File
import java.io.OutputStream

class DeprecatedCodeGenerator: CodeGenerator {
    private val errorMessage = "You cannot use `SymbolProcessorEnvironment.codeGenerator`, you must use `StratifySymbolProcessor.codeGenerator` instead."

    override val generatedFile: Collection<File>
        get() = throw IllegalAccessException(errorMessage)

    override fun associate(sources: List<KSFile>, packageName: String, fileName: String, extensionName: String)
            = throw IllegalAccessException(errorMessage)

    override fun associateByPath(sources: List<KSFile>, path: String, extensionName: String)
            = throw IllegalAccessException(errorMessage)

    override fun associateWithClasses(
        classes: List<KSClassDeclaration>,
        packageName: String,
        fileName: String,
        extensionName: String
    )  = throw IllegalAccessException(errorMessage)

    override fun createNewFile(
        dependencies: Dependencies,
        packageName: String,
        fileName: String,
        extensionName: String
    ): OutputStream = throw IllegalAccessException(errorMessage)

    override fun createNewFileByPath(dependencies: Dependencies, path: String, extensionName: String): OutputStream
            = throw IllegalAccessException(errorMessage)

}