plugins {
    kotlin("jvm")
}

val ARTIFACT_ID = "Stratify.Test"
val publicationName = "stratifyTest"

dependencies {
    api(libs.compile.testing.ksp)
    api(libs.compile.testing)
    implementation(libs.truth)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}