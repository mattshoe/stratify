# **Stratify**
**Stratify** enables you to build Kotlin Symbol Processing (KSP) plugins more easily than ever before. **Stratify** abstracts 
away nearly all of the boilerplate of writing a KSP plugin, and integrates Kotlin coroutines into your KSP code to 
maximize the efficiency of your Symbol Processors.

## Features
- **Efficiency**: Take advantage of built-in support for Coroutines to increase efficiency.
- **Flexibility**: Define any number of `Processors` for a given annotation, and control the order in which they run.
- **Simplicity**: Simple define `Processor`, plug it into a `Strategy`, and **Stratify** will do the rest!
- **Scalability**: Designed to handle growing projects, **Stratify**'s robust framework encourages scalable and sustainable development practices, making it ideal for both small teams and large enterprises.
- **Strategy Pattern**: Makes use of the strategy pattern for flexible and maintainable code generation.

## Benefits

- **Less Code, More Features**: **Stratify** abstracts away the complex and tedious boilerplate, enabling developers to focus on the fun stuff.
- **Architecture**: By enforcing a consistent architecture with the strategy pattern, **Stratify** can help keep your codebases clean, modular, and easy to manage.
- **Coroutines**: With **Stratify's** built-in coroutines support, you get efficient, non-blocking operations, improving performance in large-scale projects.
- **Rapid Prototyping and Testing**: Developers can quickly implement and experiment with new processors, accelerating the development cycle.

# Overview
With **Stratify** all you need to do is set up a `Strategy` and a `Processor`, and **Stratify** will automate the rest of the boilerplate
to select nodes efficiently. A `Strategy` defines which nodes to visit, and a `Processor` defines an operation to perform on each of
those nodes. You can have any number of strategies, and each one can have any number of processors. This is an extremely 
powerful way to build your KSP plugin, because your code will be easy to understand, easy to change, and infinitely flexible.
The **Stratify** framework will keep your code clean, keep your architecture scalable, simplify maintenance, and make experimentation
as easy as swapping in a new processor.

# Quick Start

### 1. Add Dependencies
The **Stratify** framework will transitively provide you with the KSP
libraries you need for your development as well, so you only need one dependency.

Add the following to your `build.gradle.kts`
```kotlin
dependencies {
    // Note that this will also provide the KSP libraries you need!
    implementation("io.github.mattshoe.shoebox:Stratify:1.0.0")  
}
```

### 2. Create an Annotation (Optional)
If your `Processor` relies on a custom annotation, now is the time!
```kotlin
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class MyAnnotation
```

### 3. Implement a `StratifySymbolProcessor`
Extend `StratifySymbolProcessor` and implement the `strategies` list.

```kotlin
class MyProcessor(
    environment: SymbolProcessorEnvironment
) : StratifySymbolProcessor(
    environment
) {
    override val strategies = listOf(
        AnnotationStrategy(
            annotation = MyAnnotation::class,
            TODO("Add your processors here once you implement them")
        )
    )
}
```

### 4. Create a `SymbolProcessorProvider`
**Stratify** abstracts this step away for you, all you need to do is the following:
```kotlin
class MyProcessorProvider: SymbolProcessorProvider by StratifyProvider<MyProcessor>()
```

### 5. Add your META-INF File
KSP requires you to have a metadata file that points to your provider from Step 4.

Just create the following file:<br>
`src/main/resources/META-INF/services/com.google.devtools.ksp.processing.SymbolProcessorProvider`

And inside the file just put the fully qualified name of your `SymbolProcessorProvider` from step 4
```
com.foo.bar.MyProcessorProvider
```

### 7. Implement a `Processor`

Take the simple `Processor` below. This processor inspects the KDoc on any class declaration, then uses Kotlin Poet 
to generate an extension function which returns the KDoc as a string:
```kotlin
class DocReaderClassProcessor: Processor<KSClassDeclaration> { // Specify we're only interested in KSClassDeclaration
    override val targetClass = KSClassDeclaration::class // The class of your generic type 

    override suspend fun process(node: KSClassDeclaration): Set<GeneratedFile> {
        val packageName = node.packageName.asString()
        val className = node.simpleName.asString()
        val fileName = "${className}_DocReader"

        // Generate a file that defines an extension function `SomeClass.readDoc()` 
        val readDocFunction = FunSpec.builder("readDoc")
            .receiver(ClassName(packageName, className))
            .returns(String::class)
            .addStatement("return %S", node.docString ?: "")
            .build()
        val file = FileSpec.builder(packageName, fileName)
            .addFunction(readDocFunction)
            .build()

        // Return the set of files that we've generated for this node
        return setOf(
            GeneratedFile(
                packageName = packageName,
                fileName = fileName,
                output = file.toString()
            )
        )
    }
}
```

### 8. Choose a `Strategy` and plug in your `Processor`!
The final step is to just choose your `Strategy` and plug it into your `StratifySymbolProcessor`!
```kotlin
class MyProcessor(
    environment: SymbolProcessorEnvironment
) : StratifySymbolProcessor(
    environment
) {
    override val strategies = listOf(
        AnnotationStrategy(
            annotation = MyAnnotation::class,
            DocReaderClassProcessor()
        )
    )
}
```




# What is a Strategy?
In the context of **Stratify**, a strategy simply defines a set of operations to run against a specific subset of `KSNode` instances.
For example, you may have a strategy to run a set of operations against all source code annotated with a specific Annotation. 
Or perhaps you need to run a set of operations against files matching a specific naming convention. There are many use cases
you may come across.

### Case Study
The most common use-case by far will likely be the `AnnotationStrategy` which defines a set of `Processors` to run against 
all nodes annotated with a specific annotation.
```kotlin
AnnotationStrategy(
    annotation = MyAnnotation::class,
    MyClassProcessor(),
    MyFunctionProcessor()
)
```

# What is a Processor?
In the context of **Stratify**, a `Processor` is a class built to handle exactly one type of KSNode. It simply defines the
operation to run against a specific type of `KSNode`. This is most often used to generate a new code file, but you can 
leverage a `Processor` to run any type of operation you may need. You may use it to aggregate data, or some other 
use-case you may find.

### Case Study
Take this simple `Processor` below. 
This processor inspects the KDoc on a class declaration, then uses Kotlin Poet to generate an extension function
which returns the KDoc as a string.

Note that a `Processor` is not tied to any particular annotation, it simply runs against all the `KSNode` resolved by your 
`Strategy`. For example, using this in a `FilePatternStrategy` would mean this processor would only run against the classes
that are within files matching the specified file pattern.

```kotlin
class DocReaderClassProcessor: Processor<KSClassDeclaration> { // Specify we're only interested in KSClassDeclaration
    override val targetClass = KSClassDeclaration::class // The class of your generic type 

    override suspend fun process(node: KSClassDeclaration): Set<GeneratedFile> {
        val packageName = node.packageName.asString()
        val className = node.simpleName.asString()
        val fileName = "${className}_DocReader"

        // Generate a file that defines an extension function `SomeClass.readDoc()` 
        val readDocFunction = FunSpec.builder("readDoc")
            .receiver(ClassName(packageName, className))
            .returns(String::class)
            .addStatement("return %S", node.docString ?: "")
            .build()
        val file = FileSpec.builder(packageName, fileName)
            .addFunction(readDocFunction)
            .build()

        // Return the set of files that we've generated for this node
        return setOf(
            GeneratedFile(
                packageName = packageName,
                fileName = fileName,
                output = file.toString()
            )
        )
    }
}
```

# Built-In Strategies
### AnnotationStrategy
Defines a `Strategy` whose processors will receive all instances of any `KSAnnotated` node which is annotated by the 
specified annotation.
```kotlin
AnnotationStrategy(
    annotation = DocReader::class,
    DocReaderClassProcessor(),
    DocReaderFunctionProcessor()
)
```

### FilePatternStrategy
Defines a `Strategy` whose processors will receive all instances of any `KSFile` nodes whose name matches the given pattern.
```kotlin
FilePatternStrategy(
    pattern = ".*ViewModel",
    DocReaderClassProcessor(),
    DocReaderFunctionProcessor()
)
```

### FileNameStrategy
Defines a `Strategy` whose processors will receive all instances of any `KSFile` nodes whose name exactly matches the 
given name.
```kotlin
FileNameStrategy(
    name = "SomeFileName",
    DocReaderClassProcessor(),
    DocReaderFunctionProcessor()
)
```

### FunctionPatternStrategy
Defines a `Strategy` whose processors will receive all instances of any `KSFunctionDeclaration` nodes whose name matches 
the given pattern.
```kotlin
FunctionPatternStrategy(
    pattern = ".*SomeFunction",
    DocReaderFunctionProcessor()
)
```

### FunctionNameStrategy
Defines a `Strategy` whose processors will receive all instances of any `KSFunctionDeclaration` nodes whose name exactly matches the
given name.
```kotlin
FunctionNameStrategy(
    name = "someFunctionName",
    DocReaderFunctionProcessor()
)
```

### PropertyPatternStrategy
Defines a `Strategy` whose processors will receive all instances of any `KSPropertyDeclaration` nodes whose name matches
the given pattern.
```kotlin
PropertyPatternStrategy(
    pattern = ".*SomeProperty",
    DocReaderPropertyProcessor()
)
```

### PropertyNameStrategy
Defines a `Strategy` whose processors will receive all instances of any `KSPropertyDeclaration` nodes whose name exactly matches the
given name.
```kotlin
PropertyNameStrategy(
    name = "somePropertyName",
    DocReaderFunctionProcessor()
)
```

### NewFilesStrategy
Defines a `Strategy` whose processors will receive all new `KSFile` instances. The term "new" here is as defined by `com.google.devtools.ksp.processing.Resolver.getNewFiles`.

```kotlin
NewFilesStrategy(
    DocReaderClassProcessor()
)
```