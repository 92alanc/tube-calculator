plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.alancamargo.tubecalculator.home"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner =
            "com.alancamargo.tubecalculator.core.test.runner.InstrumentedTestRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    packaging {
        resources.excludes.add("META-INF/*")
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
    implementation(project(":common"))
    implementation(project(":navigation"))

    implementation(libs.android.activity)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)
    implementation(libs.google.ads)
    implementation(libs.hilt.android)

    ksp(libs.hilt.compiler)

    testImplementation(project(":core-test"))

    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)

    androidTestImplementation(project(":core-test"))

    androidTestImplementation(libs.android.espresso.contrib) {
        exclude(module = "protobuf-lite")
    }
    androidTestImplementation(libs.android.espresso.core)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.truth)

    androidTestUtil(libs.android.test.orchestrator)

    kspAndroidTest(libs.hilt.compiler)
}
