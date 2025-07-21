plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelise)
}

android {
    namespace = "com.alancamargo.tubecalculator.common"
    compileSdk = Config.Build.TARGET_SDK

    defaultConfig {
        minSdk = Config.Build.MIN_SDK
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.Build.IS_MINIFY_ENABLED
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
    implementation(project(Config.Modules.CORE_DESIGN))

    implementation(libs.android.core)
}
