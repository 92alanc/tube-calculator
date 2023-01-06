package com.alancamargo.tubecalculator.settings.ui.viewmodel

internal data class SettingsViewState(val isCrashLoggingEnabled: Boolean = false) {

    fun setCrashLoggingEnabled(
        isCrashLoggingEnabled: Boolean
    ) = copy(isCrashLoggingEnabled = isCrashLoggingEnabled)
}
