plugins {
    kotlin("multiplatform")
    id("maven-publish")
    signing
}

ext {
    set("ARTIFACT_ID", "Stratify")
    set("PUBLICATION_NAME", "stratify")
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    js {
        browser()
        nodejs()
    }
    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines)
            implementation(kotlin("reflect"))
        }
        iosMain {
            dependsOn(commonMain.get())
        }
        jsMain {
            dependsOn(commonMain.get())
        }
        jvmMain {
            dependsOn(commonMain.get())
            dependencies {
                api(libs.ksp.symbol.processing.api)

            }
        }
    }
}

