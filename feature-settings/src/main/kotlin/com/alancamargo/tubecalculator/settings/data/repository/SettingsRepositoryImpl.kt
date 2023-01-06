package com.alancamargo.tubecalculator.settings.data.repository

import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

private const val KEY_CRASH_LOGGING = "is_crash_logging_enabled"

internal class SettingsRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val logger: Logger
) : SettingsRepository {

    override fun setCrashLoggingEnabled(isEnabled: Boolean) {
        preferencesManager.putBoolean(KEY_CRASH_LOGGING, isEnabled)
        logger.setCrashLoggingEnabled(isEnabled)
    }

    override fun isCrashLoggingEnabled(): Boolean {
        return preferencesManager.getBoolean(KEY_CRASH_LOGGING, defaultValue = false)
    }
}
