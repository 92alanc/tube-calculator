import org.gradle.api.JavaVersion

object Config {

    object Build {

        const val MIN_SDK = 24
        const val TARGET_SDK = 36
        const val VERSION_CODE = 17
        const val VERSION_NAME = "2025.3.0"
        const val IS_MINIFY_ENABLED = true
        const val RELEASE_BUILD_TYPE = "release"
        const val IS_VIEW_BINDING_ENABLED = true
        const val META_INF_DIR = "META-INF/*"

        val javaVersion = JavaVersion.VERSION_17
        val javaVersionInt = javaVersion.majorVersion.toInt()
    }

    object Modules {

        const val COMMON = ":common"
        const val CORE = ":core"
        const val CORE_DESIGN = ":core-design"
        const val CORE_TEST = ":core-test"
        const val FEATURE_FARES = ":feature-fares"
        const val FEATURE_HOME = ":feature-home"
        const val FEATURE_SEARCH = ":feature-search"
        const val FEATURE_SETTINGS = ":feature-settings"
        const val NAVIGATION = ":navigation"
    }

    object Testing {

        const val ANDROID_TEST_ORCHESTRATOR_NAME = "ANDROIDX_TEST_ORCHESTRATOR"
        const val ANIMATIONS_DISABLED = true
        const val KEY_CLEAR_PACKAGE_DATA = "clearPackageData"
        const val VALUE_CLEAR_PACKAGE_DATA = "true"
        const val CUSTOM_TEST_RUNNER = "com.alancamargo.tubecalculator.core.test.runner.InstrumentedTestRunner"
        const val PROTOBUF_LITE_DEPENDENCY = "protobuf-lite"
    }
}
