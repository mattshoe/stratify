package test.stratify.processors

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import io.github.mattshoe.shoebox.stratify.model.GeneratedFile
import io.github.mattshoe.shoebox.stratify.processor.Processor

class DocReaderClassProcessor: Processor<KSClassDeclaration> {
    override val targetClass = KSClassDeclaration::class

    override suspend fun process(node: KSClassDeclaration): Set<GeneratedFile> {
        val packageName = node.packageName.asString()
        val className = node.simpleName.asString()
        val fileName = "${className}_DocReader"

        val readDocFunction = FunSpec.builder("readDoc")
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