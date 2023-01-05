package com.alancamargo.tubecalculator.core.tools

import android.util.Log
import javax.inject.Inject

private const val LOG_TAG = "TUBE_CALCULATOR_LOG"

internal class LoggerImpl @Inject constructor() : Logger {

    override fun debug(message: String) {
        Log.d(LOG_TAG, message)
    }

    override fun error(throwable: Throwable) {
        Log.e(LOG_TAG, throwable.message, throwable)
    }
}
