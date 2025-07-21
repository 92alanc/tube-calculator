plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialisation)
}

android {
    namespace = "com.alancamargo.tubecalculator.core"
    compileSdk = Config.Build.TARGET_SDK

    defaultConfig {
        minSdk = Config.Build.MIN_SDK

        buildConfigField("String", "APP_ID", "\"${System.getenv("TUBE_CALCULATOR_APP_ID")}\"")
        buildConfigField("String", "APP_KEY", "\"${System.getenv("TUBE_CALCULATOR_APP_KEY")}\"")
        buildConfigField("String", "BASE_URL", "\"${System.getenv("TUBE_CALCULATOR_BASE_URL")}\"")

        /*buildConfigField("String", "APP_ID", "\"$tube_calculator_app_id\"")
        buildConfigField("String", "APP_KEY", "\"$tube_calculator_app_key\"")
        buildConfigField("String", "BASE_URL", "\"$tube_calculator_base_url\"")*/
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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = Config.Build.javaVersion
        targetCompatibility = Config.Build.javaVersion
    }
    buildFeatures {
        buildConfig = true
    }

    kotlin {
        jvmToolchain(Config.Build.javaVersionInt)
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))

    implementation(libs.android.appcompat)
    implementation(libs.android.material)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.config)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.firestore)
    implementation(libs.hilt.android)
    implementation(libs.kotlin.serialisation.converter)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.work)

    api(libs.android.activity)
    api(libs.kotlin.serialisation)
    api(libs.retrofit)

    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)

    testImplementation(libs.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)
}
