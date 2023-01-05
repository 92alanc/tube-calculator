package com.alancamargo.tubecalculator.core.tools

interface Logger {

    fun setCrashLoggingEnabled(isEnabled: Boolean)

    fun debug(message: String)

    fun error(throwable: Throwable)
}
