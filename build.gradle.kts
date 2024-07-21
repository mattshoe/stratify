plugins {
    kotlin("jvm") version "1.9.23"
    id("maven-publish")
    signing
}

val GROUP_ID: String = project.properties["group.id"].toString()
val VERSION: String = project.properties["version"].toString()

group = GROUP_ID
version = VERSION

repositories {
    mavenCentral()
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }

    group = GROUP_ID
    version = VERSION

    plugins.withId("org.jetbrains.kotlin.jvm") {
        kotlin {
            jvmToolchain(19)
        }
    }

    tasks.withType<Test> {
        useJUnit()
        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}

dependencies {
    implementation(libs.ksp.symbol.processing.api)
    implementation(libs.kotlinx.coroutines)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}
