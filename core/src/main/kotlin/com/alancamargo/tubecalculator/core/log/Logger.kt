package com.alancamargo.tubecalculator.core.log

interface Logger {

    fun setCrashLoggingEnabled(isEnabled: Boolean)

    fun debug(message: String)

    fun error(throwable: Throwable)
}
