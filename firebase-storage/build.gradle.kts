import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

version = project.property("firebase-storage.version") as String

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.5.30"
}

android {
    compileSdk = property("targetSdkVersion") as Int
    defaultConfig {
        minSdk = property("minSdkVersion") as Int
        targetSdk = property("targetSdkVersion") as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
        getByName("androidTest") {
            java.srcDir(file("src/androidAndroidTest/kotlin"))
            manifest.srcFile("src/androidAndroidTest/AndroidManifest.xml")
        }
    }
    testOptions {
        unitTests.apply {
            isIncludeAndroidResources = true
        }
    }
    packagingOptions {
        resources.pickFirsts.add("META-INF/kotlinx-serialization-core.kotlin_module")
        resources.pickFirsts.add("META-INF/AL2.0")
        resources.pickFirsts.add("META-INF/LGPL2.1")
        resources.pickFirsts.add("androidsupportmultidexversion.txt")
    }
    lint {
        isAbortOnError = false
    }
}

kotlin {

    android {
        publishAllLibraryVariants()
    }

    fun nativeTargetConfig(): KotlinNativeTarget.() -> Unit = {
        val nativeFrameworkPaths = listOf(
            rootProject.project("firebase-app").projectDir.resolve("src/nativeInterop/cinterop/Carthage/Build/iOS")
        ).plus(
            listOf(
                "FirebaseAnalytics",
                "FirebaseCore",
                "FirebaseCoreDiagnostics",
                "FirebaseInstallations",
                "GoogleAppMeasurement",
                "GoogleDataTransport",
                "GoogleUtilities",
                "nanopb",
                "PromisesObjC"
            ).map {
                val archVariant =
                    if (konanTarget is org.jetbrains.kotlin.konan.target.KonanTarget.IOS_X64) "ios-arm64_i386_x86_64-simulator" else "ios-arm64_armv7"

                rootProject.project("firebase-app").projectDir.resolve("src/nativeInterop/cinterop/Carthage/Build/$it.xcframework/$archVariant")
            }
        ).plus(
            listOf(
                "FirebaseStorage",
            ).map {
                val archVariant =
                    if (konanTarget is org.jetbrains.kotlin.konan.target.KonanTarget.IOS_X64) "ios-arm64_i386_x86_64-simulator" else "ios-arm64_armv7"

                projectDir.resolve("src/nativeInterop/cinterop/Carthage/Build/$it.xcframework/$archVariant")
            }
        )

        binaries {
            getTest("DEBUG").apply {
                linkerOpts(nativeFrameworkPaths.map { "-F$it" })
                linkerOpts("-ObjC")
            }
        }

        compilations.getByName("main") {
            cinterops.create("FirebaseStorage") {
                compilerOpts(nativeFrameworkPaths.map { "-F$it" })
                extraOpts("-verbose")
            }
        }
    }

    if (project.extra["ideaActive"] as Boolean) {
        iosX64("ios", nativeTargetConfig())
    } else {
        ios(configure = nativeTargetConfig())
    }

    js {
        useCommonJs()
        nodejs {
            testTask {
                useMocha {
                    timeout = "5s"
                }
            }
        }
        browser {
            testTask {
                useMocha {
                    timeout = "5s"
                }
            }
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                apiVersion = "1.5"
                languageVersion = "1.5"
                progressiveMode = true
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.serialization.InternalSerializationApi")
            }
        }

        val commonMain by getting {
            dependencies {
                api(project(":firebase-app"))
                implementation(project(":firebase-common"))
            }
        }

        val androidMain by getting {
            dependencies {
                api("com.google.firebase:firebase-storage-ktx")
            }
        }

        val iosMain by getting

        val jsMain by getting
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}
