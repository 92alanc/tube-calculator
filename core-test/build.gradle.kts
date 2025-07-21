plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.alancamargo.tubecalculator.core.test"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    implementation(project(":core-design"))

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
