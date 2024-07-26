plugins {
    kotlin("jvm")
    id("maven-publish")
    signing
}

ext {
    set("ARTIFACT_ID", "Stratify.Test")
    set("PUBLICATION_NAME", "stratifyTest")
}

dependencies {
    api(libs.compile.testing.ksp)
    api(libs.compile.testing)
    implementation(libs.truth)

    testImplementation(kotlin("test"))
}