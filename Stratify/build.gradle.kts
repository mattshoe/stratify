import kotlin.math.sign

plugins {
    kotlin("jvm")
    id("maven-publish")
    signing
}

val ARTIFACT_ID = "Stratify"
val publicationName = "stratify"

dependencies {
    api(libs.ksp.symbol.processing.api)
    implementation(libs.kotlinx.coroutines)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}

afterEvaluate {
    plugins.withId("maven-publish") {
        publishing {
            publications {
                repositories {
                    mavenLocal()
                }

                create<MavenPublication>(publicationName) {
                    from(components["java"])
                    groupId = rootProject.group.toString()
                    artifactId = ARTIFACT_ID
                    version = rootProject.version.toString()
                    pom {
                        name = "Stratify"
                        description = "Stratify: Simplify KSP Plugin Development"
                        url = "https://github.com/mattshoe/stratify"
                        properties = mapOf(
                            "myProp" to "value"
                        )
                        packaging = "jar"
                        inceptionYear = "2024"
                        licenses {
                            license {
                                name = "The Apache License, Version 2.0"
                                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                            }
                        }
                        developers {
                            developer {
                                id = "mattshoe"
                                name = "Matthew Shoemaker"
                                email = "mattshoe81@gmail.com"
                            }
                        }
                        scm {
                            connection = "scm:git:git@github.com:mattshoe/stratify.git"
                            developerConnection = "scm:git:git@github.com:mattshoe/stratify.git"
                            url = "https://github.com/mattshoe/stratify"
                        }
                    }
                }


                signing {
                    val signingKey = providers
                        .environmentVariable("GPG_SIGNING_KEY")
                        .forUseAtConfigurationTime()
                    val signingPassphrase = providers
                        .environmentVariable("GPG_SIGNING_PASSPHRASE")
                        .forUseAtConfigurationTime()
                    if (signingKey.isPresent && signingPassphrase.isPresent) {
                        useInMemoryPgpKeys(signingKey.get(), signingPassphrase.get())
                        sign(publishing.publications[publicationName])
                    }
                }
            }
        }
    }

    tasks.register<Zip>("generateZip") {
        val publishTask = tasks.named(
            "publish${publicationName.replaceFirstChar { it.uppercaseChar() }}PublicationToMavenLocalRepository",
            PublishToMavenRepository::class.java
        )
        from(publishTask.map { it.repository.url })
        archiveFileName.set("stratify_${rootProject.version}.zip")
    }
}

