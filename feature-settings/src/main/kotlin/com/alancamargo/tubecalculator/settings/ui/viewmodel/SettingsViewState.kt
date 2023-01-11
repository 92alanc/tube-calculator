package com.alancamargo.tubecalculator.settings.ui.viewmodel

internal data class SettingsViewState(
    val isCrashLoggingEnabled: Boolean = false,
    val isAdPersonalisationEnabled: Boolean = false,
    val isAnalyticsEnabled: Boolean = false
) {

    fun setCrashLoggingEnabled(
        isCrashLoggingEnabled: Boolean
    ) = copy(isCrashLoggingEnabled = isCrashLoggingEnabled)

    fun setAdPersonalisationEnabled(
        isAdPersonalisationEnabled: Boolean
    ) = copy(isAdPersonalisationEnabled = isAdPersonalisationEnabled)

    fun setAnalyticsEnabled(
        isAnalyticsEnabled: Boolean
    ) = copy(isAnalyticsEnabled = isAnalyticsEnabled)

    fun setAllValues(
        isCrashLoggingEnabled: Boolean,
        isAdPersonalisationEnabled: Boolean,
        isAnalyticsEnabled: Boolean
    ) = copy(
        isCrashLoggingEnabled = isCrashLoggingEnabled,
        isAdPersonalisationEnabled = isAdPersonalisationEnabled,
        isAnalyticsEnabled = isAnalyticsEnabled
    )
}
