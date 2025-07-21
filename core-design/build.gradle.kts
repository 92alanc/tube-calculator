plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.alancamargo.tubecalculator.core.design"
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
    implementation(project(Config.Modules.CORE))

    implementation(libs.android.material)
    implementation(libs.coil)
    implementation(libs.coil.gif)
    implementation(libs.google.ads)
    implementation(libs.hilt.android)

    api(libs.android.splashscreen)

    ksp(libs.hilt.compiler)
}
