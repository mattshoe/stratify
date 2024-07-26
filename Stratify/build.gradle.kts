plugins {
    kotlin("jvm")
    id("maven-publish")
    signing
}

ext {
    set("ARTIFACT_ID", "Stratify")
    set("PUBLICATION_NAME", "stratify")
}

dependencies {
    api(libs.ksp.symbol.processing.api)
    implementation(libs.kotlinx.coroutines)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}

