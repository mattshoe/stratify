# Stratify
Stratify enables you to build Kotlin Symbol Processing (KSP) plugins more easily than ever before. Stratify abstracts 
away nearly all of the boilerplate of writing a KSP plugin, and integrates Kotlin coroutines into your KSP code to 
maximize the efficiency of your Symbol Processors.

## Features
- **Strategy Pattern**: Makes use of the strategy pattern for flexible and maintainable code generation.
- **Coroutines**: Leverages Kotlin coroutines to handle your tasks as efficiently as possible.
- **Flexibility**: Define any number of `Processors` for a given annotation, and control the order in which they run.
- **Simplicity**: Just define one or more `Processors`, plug it into a `Strategy`, and Stratify will do the rest!
- **Scalability**: Designed to handle growing projects, Stratify's robust framework encourages scalable and sustainable development practices, making it ideal for both small teams and large enterprises.

## Benefits

- **Less Code, More Features**: Stratify abstracts complex boilerplate, enabling developers to focus on the core logic of their processors without repetitive setup tasks.
- **Architecture**: By enforcing a consistent architecture with the strategy pattern, Stratify can help keep your codebases clean, modular, and easy to manage.
- **Coroutines**: With built-in coroutines support, Stratify allows for efficient, non-blocking operations, significantly improving performance in large-scale projects.
- **Rapid Prototyping and Testing**: Developers can quickly implement and experiment with new processors, accelerating the development cycle.

## Quick Start

### 1. Add Dependencies

Add the following to your `build.gradle.kts`:

```kotlin
dependencies {
    // Note that this will also provide the KSP libraries you need!
    implementation("com.github.mattshoe:stratify:1.0.0")  
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
Extend the StratifySymbolProcessor class and just implement the `strategies` list.

```kotlin
class MyProcessor(
    environment: SymbolProcessorEnvironment
) : StratifySymbolProcessor(
    environment
) {
    override val strategies = listOf(
        AnnotationStrategy(
            annotation = MyAnnotation::class,
            MyClassProcessor(),
            MyFunctionProcessor()
        ),
        FilePatternStrategy(
            pattern = ".*ViewModel",
            MyViewModelProcessor()
        ),
        PropertyNameStrategy(
            name = "state",
            MyFirstStatePropertyProcessor(),
            MySecondStatePropertyProcessor()
        )
        // etc, etc
    )
}
```

### 4. Create a `SymbolProcessorProvider`
Stratify abstracts this step away for you, all you need to do is the following:
```kotlin
class MyProcessorProvider: SymbolProcessorProvider by stratifyProvider<MyProcessor>()
```

### 5. Add your META-INF File
KSP requires you to have a metadata file that points to your provider.

Create the following file:<br>
`src/main/resources/META-INF/services/com.google.devtools.ksp.processing.SymbolProcessorProvider`

Inside the file you only need to put the fully qualified name of your `SymbolProcessorProvider` from step 4
```
com.foo.my.project.MyProcessorProvider
```

### 6. Build
Now your project is set up and ready to build and run.
```shell
./gradlew clean build
```

## What is a Strategy?
In the context of Stratify, a strategy simply defines a set of operations to run against a specific subset of `KSNode` instances.
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

