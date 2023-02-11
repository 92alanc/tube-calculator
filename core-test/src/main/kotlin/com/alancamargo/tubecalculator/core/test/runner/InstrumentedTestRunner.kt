package com.alancamargo.tubecalculator.core.test.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

// Only used in Gradle
@Suppress("unused")
class InstrumentedTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(
            classLoader,
            HiltTestApplication::class.java.name,
            context
        )
    }
}
