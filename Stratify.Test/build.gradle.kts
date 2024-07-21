plugins {
    kotlin("jvm")
}

group = "io.github.mattshoe.shoebox"
version = "1.0.0.0"

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
    testImplementation(libs.compile.testing.ksp)
    testImplementation(libs.compile.testing)
    testImplementation(libs.truth)
    testImplementation(libs.junit)
}