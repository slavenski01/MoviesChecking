plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqdelight)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    targets.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().forEach {
        it.binaries.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>()
            .forEach { lib ->
                lib.isStatic = false
                lib.linkerOpts.add("-lsqlite3")
            }
    }

    sourceSets {
        commonMain.dependencies {
            //sqlite
            implementation(libs.bundles.sqldelight.common)

            //ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization)

            //coroutines
            implementation(libs.kotlin.coroutines)

            //datetime
            implementation(libs.kotlin.dateteime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain {
            dependencies {
                //sqlite
                implementation(libs.sqlite.android.driver)

                //ktor
                implementation(libs.ktor.client.android)
            }
            dependsOn(commonMain.get())
        }

        val desktopMain by getting
        desktopMain.apply {
            dependencies {
                //sqlite
                implementation(libs.sqlite.jvmMain.driver)

                //ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
            }
            dependsOn(commonMain.get())
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain.get())
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        iosMain {
            dependencies {
                //sqlite
                implementation(libs.sqlite.iosMain.driver)

                //ktor
                implementation(libs.ktor.client.ios)
            }
        }
    }
}

android {
    namespace = "com.example.project.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

sqldelight {
    databases.create("MoviesDatabase") {
        packageName.set("com.example.shared.cache.db")
    }
}