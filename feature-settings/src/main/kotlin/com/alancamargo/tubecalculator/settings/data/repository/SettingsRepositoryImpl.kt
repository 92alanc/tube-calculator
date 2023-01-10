package com.alancamargo.tubecalculator.settings.data.repository

import com.alancamargo.tubecalculator.core.analytics.Analytics
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

private const val KEY_AD_PERSONALISATION = "is_ad_personalisation_enabled"
private const val KEY_ANALYTICS = "is_analytics_enabled"
private const val KEY_CRASH_LOGGING = "is_crash_logging_enabled"

internal class SettingsRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val analytics: Analytics,
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
        preferencesManager.putBoolean(KEY_AD_PERSONALISATION, isEnabled)
        analytics.setAdPersonalisationEnabled(isEnabled)
    }

    override fun isAdPersonalisationEnabled(): Boolean {
        return preferencesManager.getBoolean(KEY_AD_PERSONALISATION, defaultValue = false)
    }

    override fun setAnalyticsEnabled(isEnabled: Boolean) {
        analytics.setAnalyticsEnabled(isEnabled)
        preferencesManager.putBoolean(KEY_ANALYTICS, isEnabled)
    }

    override fun isAnalyticsEnabled(): Boolean {
        return preferencesManager.getBoolean(KEY_ANALYTICS, defaultValue = false)
    }
}
