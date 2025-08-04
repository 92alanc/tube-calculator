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
    compileSdk = Config.Build.TARGET_SDK

    defaultConfig {
        applicationId = "com.alancamargo.tubecalculator"
        minSdk = Config.Build.MIN_SDK
        targetSdk = Config.Build.TARGET_SDK
        versionCode = Config.Build.VERSION_CODE
        versionName = Config.Build.VERSION_NAME
        testInstrumentationRunner = Config.Testing.CUSTOM_TEST_RUNNER
    }

    signingConfigs {
        /*create(Config.Build.RELEASE_BUILD_TYPE) {
            keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
            storeFile = file(System.getenv("HOME") + "/keystores/tube-calculator.jks")
            storePassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PASSWORD")
        }*/

        create(Config.Build.RELEASE_BUILD_TYPE) {
            keyAlias = properties["tube_calculator_key_alias"] as String
            keyPassword = properties["tube_calculator_key_password"] as String
            storeFile = file(path = properties["tube_calculator_store_file"] as String)
            storePassword = properties["tube_calculator_store_password"] as String
        }
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
            signingConfig = signingConfigs.getByName(Config.Build.RELEASE_BUILD_TYPE)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = Config.Build.javaVersion
        targetCompatibility = Config.Build.javaVersion
    }

    kotlin {
        jvmToolchain(Config.Build.javaVersionInt)
    }
}

dependencies {
    implementation(project(Config.Modules.CORE))
    implementation(project(Config.Modules.CORE_DESIGN))
    implementation(project(Config.Modules.FEATURE_HOME))
    implementation(project(Config.Modules.FEATURE_SEARCH))
    implementation(project(Config.Modules.FEATURE_FARES))
    implementation(project(Config.Modules.FEATURE_SETTINGS))

    implementation(libs.google.ads)
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)

    ksp(libs.hilt.compiler)
}
