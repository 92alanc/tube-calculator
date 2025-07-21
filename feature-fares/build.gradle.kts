plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelise)
    alias(libs.plugins.kotlin.serialisation)
}

android {
    namespace = "com.alancamargo.tubecalculator.fares"
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

    sourceSets {
        getByName("main") {
            assets.setSrcDirs(listOf("src/main/assets", "src/androidTest/assets"))
        }
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
    implementation(libs.hilt.work)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.shimmer)
    implementation(libs.work)

    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)

    testImplementation(project(":core-test"))

    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)

    androidTestImplementation(project(":core-test"))

    androidTestImplementation(libs.android.espresso.core)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.truth)

    androidTestUtil(libs.android.test.orchestrator)

    kspAndroidTest(libs.hilt.compiler)
}
