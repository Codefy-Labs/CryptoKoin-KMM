plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

group = "com.phoenix.analytics"
version = "0.1"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "analytics module"
        homepage = ""
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "analytics"
        }
        pod("Mixpanel") {
            version = "~> 5.0.5"
        }

    }

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            implementation("io.insert-koin:koin-core:3.2.0")
        }


        androidMain.dependencies {
            implementation("com.mixpanel.android:mixpanel-android:6.2.2")

            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:32.7.4"))
            implementation("com.google.firebase:firebase-analytics")
        }


        iosMain.dependencies {

        }

    }
}

android {
    namespace = "com.phoenix.analytics"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
    }
}