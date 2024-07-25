package test.stratify.processors

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import io.github.mattshoe.shoebox.stratify.model.GeneratedFile
import io.github.mattshoe.shoebox.stratify.processor.Processor

class DocReaderFunctionProcessor: Processor<KSFunctionDeclaration> {
    override val targetClass = KSFunctionDeclaration::class

    override suspend fun process(node: KSFunctionDeclaration): Set<GeneratedFile> {
        val packageName = node.packageName.asString()
        val className = (node.parentDeclaration as? KSClassDeclaration)?.simpleName?.asString() ?: ""
        val functionName = node.simpleName.asString()
        val fileName = "${packageName.replace(".", "")}${functionName}_DocReader"

        val readDocFunction = FunSpec.builder("readDocFor${functionName.capitalize()}")
            .receiver(ClassName(packageName, className))
            .returns(String::class)
            .addStatement("return %S", node.docString ?: "")
            .build()
        val file = FileSpec.builder(packageName, fileName)
            .addFunction(readDocFunction)
            .build()

        return setOf(
            GeneratedFile(
                packageName = packageName,
                fileName = fileName,
                output = file.toString()
            )
        )
    }


}