plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelise)
    alias(libs.plugins.kotlin.serialisation)
}

android {
    namespace = "com.alancamargo.tubecalculator.search"
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
        viewBinding = Config.Build.IS_VIEW_BINDING_ENABLED
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
        compilerOptions {
            freeCompilerArgs.add("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
        }
    }
}

dependencies {
    implementation(project(Config.Modules.CORE))
    implementation(project(Config.Modules.CORE_DESIGN))
    implementation(project(Config.Modules.COMMON))
    implementation(project(Config.Modules.NAVIGATION))

    implementation(libs.android.activity)
    implementation(libs.android.appcompat)
    implementation(libs.android.compose.activity)
    implementation(platform(libs.android.compose.bom))
    implementation(libs.android.compose.material3)
    implementation(libs.android.compose.material.icons)
    implementation(libs.android.compose.preview)
    implementation(libs.android.fragment)
    implementation(libs.android.material)
    implementation(libs.google.ads)
    implementation(libs.hilt.android)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)

    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)

    testImplementation(project(Config.Modules.CORE_TEST))

    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)

    androidTestImplementation(project(Config.Modules.CORE_TEST))

    androidTestImplementation(libs.android.espresso.contrib) {
        exclude(module = Config.Testing.PROTOBUF_LITE_DEPENDENCY)
    }
    androidTestImplementation(libs.android.espresso.core)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.truth)

    androidTestUtil(libs.android.test.orchestrator)

    kspAndroidTest(libs.hilt.compiler)
}