package com.alancamargo.tubecalculator.settings.domain.repository

internal interface SettingsRepository {

    fun setCrashLoggingEnabled(isEnabled: Boolean)

    fun isCrashLoggingEnabled(): Boolean

    fun setAdPersonalisationEnabled(isEnabled: Boolean)

    fun isAdPersonalisationEnabled(): Boolean
}
