@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }
    }
}

rootProject.name = "Tube Calculator"

include(
    ":app",
    ":core-design",
    ":core",
    ":core-test",
    ":feature-search",
    ":common",
    ":navigation",
    ":feature-fares",
    ":feature-settings",
    ":feature-home"
)
