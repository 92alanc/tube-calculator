plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.alancamargo.tubecalculator.core.test"
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

    implementation(libs.android.appcompat)
    implementation(libs.android.espresso.core)
    implementation(libs.android.material)
    implementation(libs.android.test.runner)
    implementation(libs.coroutines.test)
    implementation(libs.truth)

    api(libs.hilt.testing)
    api(libs.mock.web.server)
    api(libs.turbine)
}
