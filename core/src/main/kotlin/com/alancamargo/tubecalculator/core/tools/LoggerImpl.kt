package com.alancamargo.tubecalculator.core.tools

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

private const val LOG_TAG = "TUBE_CALCULATOR_LOG"

internal class LoggerImpl @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) : Logger {

    override fun setCrashLoggingEnabled(isEnabled: Boolean) {
        crashlytics.setCrashlyticsCollectionEnabled(isEnabled)
    }

    override fun debug(message: String) {
        Log.d(LOG_TAG, message)
        crashlytics.log(message)
    }

    override fun error(throwable: Throwable) {
        Log.e(LOG_TAG, throwable.message, throwable)
        crashlytics.recordException(throwable)
    }
}
