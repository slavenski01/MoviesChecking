import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqdelight)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.moco.resources)
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
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
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
        val desktopMain by getting

        androidMain {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)

                //sqlite
                implementation(libs.sqlite.android.driver)

                //ktor
                implementation(libs.ktor.client.android)
            }
            dependsOn(commonMain.get())
        }

        desktopMain.apply {
            dependencies {
                implementation(compose.desktop.currentOs)

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

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

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

            //moco-resources
            api(libs.moco.resources.common)
            api(libs.moco.resources.common.compose)
            api(libs.moco.resources.tests)
        }

        multiplatformResources {
            multiplatformResourcesPackage = "org.example.library"
        }
    }
}

android {
    namespace = "com.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.example.project"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases.create("AppDatabase") {
        packageName.set("com.example.shared.cache")
    }
}