plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.alancamargo.tubecalculator.settings"
    compileSdk = Config.Build.TARGET_SDK

    defaultConfig {
        minSdk = Config.Build.MIN_SDK

        testInstrumentationRunner = Config.Testing.CUSTOM_TEST_RUNNER
        testInstrumentationRunnerArguments[Config.Testing.KEY_CLEAR_PACKAGE_DATA] =
            Config.Testing.VALUE_CLEAR_PACKAGE_DATA
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = Config.Build.IS_COMPOSE_ENABLED
    }

    testOptions {
        animationsDisabled = Config.Testing.ANIMATIONS_DISABLED
        execution = Config.Testing.ANDROID_TEST_ORCHESTRATOR_NAME
    }

    packaging {
        resources.excludes.add(Config.Build.META_INF_DIR)
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
    implementation(project(Config.Modules.NAVIGATION))

    implementation(libs.android.activity)
    implementation(libs.android.appcompat)
    implementation(libs.android.compose.activity)
    implementation(platform(libs.android.compose.bom))
    implementation(libs.android.compose.material3)
    implementation(libs.android.compose.preview)
    implementation(libs.google.ads)
    implementation(libs.hilt.android)

    ksp(libs.hilt.compiler)

    testImplementation(project(Config.Modules.CORE_TEST))

    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)

    androidTestImplementation(project(Config.Modules.CORE_TEST))

    androidTestImplementation(libs.android.espresso.core)
    androidTestImplementation(libs.mockk.android)

    androidTestUtil(libs.android.test.orchestrator)

    kspAndroidTest(libs.hilt.compiler)
}
