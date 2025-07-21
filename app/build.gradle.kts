plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.alancamargo.tubecalculator"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.alancamargo.tubecalculator"
        minSdk = 24
        targetSdk = 36
        versionCode = 17
        versionName = "2025.3.0"

        testInstrumentationRunner =
            "com.alancamargo.tubecalculator.core.test.runner.InstrumentedTestRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
            storeFile = file(System.getenv("HOME") + "/keystores/tube-calculator.jks")
            storePassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PASSWORD")
        }

        /*create("release") {
            keyAlias = properties["$tube_calculator_key_alias"] as String
            keyPassword = properties["$tube_calculator_key_password"] as String
            storeFile = file(path = properties["$tube_calculator_store_file"] as String)
            storePassword = properties["$tube_calculator_store_password"] as String
        }*/
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-design"))
    implementation(project(":feature-home"))
    implementation(project(":feature-search"))
    implementation(project(":feature-fares"))
    implementation(project(":feature-settings"))

    implementation(libs.google.ads)
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)

    ksp(libs.hilt.compiler)
}
