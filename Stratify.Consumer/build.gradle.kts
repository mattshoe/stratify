plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":Stratify"))
    implementation(libs.kotlin.poet)
    implementation(libs.kotlin.poet.ksp)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.ksp.symbol.processing.api)

    testImplementation(kotlin("test"))
    testImplementation(project(":Stratify.Test"))
    testImplementation(libs.truth)
    testImplementation(libs.junit)
}