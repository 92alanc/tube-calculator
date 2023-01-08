package com.alancamargo.tubecalculator.settings.data.repository

import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

private const val KEY_CRASH_LOGGING = "is_crash_logging_enabled"
private const val KEY_AD_PERSONALISATION = "gad_has_consent_for_cookies"

private const val AD_PERSONALISATION_DISABLED = 0
private const val AD_PERSONALISATION_ENABLED = 1

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

    override fun setAdPersonalisationEnabled(isEnabled: Boolean) {
        val value = if (isEnabled) {
            AD_PERSONALISATION_ENABLED
        } else {
            AD_PERSONALISATION_DISABLED
        }

        preferencesManager.putInt(KEY_AD_PERSONALISATION, value)
    }

    override fun isAdPersonalisationEnabled(): Boolean {
        val intValue = preferencesManager.getInt(
            KEY_AD_PERSONALISATION,
            defaultValue = AD_PERSONALISATION_DISABLED
        )

        return intValue == AD_PERSONALISATION_ENABLED
    }
}
