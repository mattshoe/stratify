package test.stratify.processors

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import io.github.mattshoe.shoebox.stratify.model.GeneratedFile
import io.github.mattshoe.shoebox.stratify.processor.Processor

class DocReaderFileProcessor: Processor<KSFile> {
    override val targetClass = KSFile::class

    override suspend fun process(node: KSFile): Set<GeneratedFile> {
        val packageName = node.packageName.asString()
        val fileName = "${packageName.replace(".", "")}${node.fileName.replace(".", "")}_DocReader"

        val readDocFunction = FunSpec.builder("readDoc")
            .returns(String::class)
            .addStatement("return %S", "${node.filePath}/${node.fileName}")
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