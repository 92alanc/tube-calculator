plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.compose.compiler)
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

    buildFeatures {
        compose = Config.Build.IS_COMPOSE_ENABLED
    }

    kotlin {
        jvmToolchain(Config.Build.javaVersionInt)
        compilerOptions {
            freeCompilerArgs.add("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
        }
    }
}

dependencies {
    implementation(project(Config.Modules.CORE))

    implementation(libs.android.material)
    implementation(libs.android.compose.activity)
    implementation(platform(libs.android.compose.bom))
    implementation(libs.android.compose.material3)
    implementation(libs.android.compose.material.icons)
    implementation(libs.android.compose.preview)
    implementation(libs.coil)
    implementation(libs.coil.gif)
    implementation(libs.google.ads)
    implementation(libs.hilt.android)
    implementation(libs.shimmer.compose)

    api(libs.android.splashscreen)

    ksp(libs.hilt.compiler)
}
