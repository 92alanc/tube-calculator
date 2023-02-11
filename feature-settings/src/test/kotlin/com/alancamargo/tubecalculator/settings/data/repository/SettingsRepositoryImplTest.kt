package com.alancamargo.tubecalculator.settings.data.repository

import com.alancamargo.tubecalculator.core.analytics.Analytics
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val KEY_AD_PERSONALISATION = "is_ad_personalisation_enabled"
private const val KEY_ANALYTICS = "is_analytics_enabled"
private const val KEY_CRASH_LOGGING = "is_crash_logging_enabled"

class SettingsRepositoryImplTest {

    private val mockPreferencesManager = mockk<PreferencesManager>(relaxed = true)
    private val mockAnalytics = mockk<Analytics>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)

    private val repository = SettingsRepositoryImpl(
        mockPreferencesManager,
        mockAnalytics,
        mockLogger
    )

    @Test
    fun `setCrashLoggingEnabled should change setting on preferences manager`() {
        // WHEN
        repository.setCrashLoggingEnabled(isEnabled = true)

        // THEN
        verify { mockPreferencesManager.putBoolean(KEY_CRASH_LOGGING, value = true) }
    }

    @Test
    fun `setCrashLoggingEnabled should change setting on logger`() {
        // WHEN
        repository.setCrashLoggingEnabled(isEnabled = true)

        // THEN
        verify { mockLogger.setCrashLoggingEnabled(isEnabled = true) }
    }

    @Test
    fun `isCrashLoggingEnabled should get setting from preferences manager`() {
        // GIVEN
        every {
            mockPreferencesManager.getBoolean(KEY_CRASH_LOGGING, defaultValue = true)
        } returns false

        // WHEN
        val actual = repository.isCrashLoggingEnabled()

        // THEN
        assertThat(actual).isFalse()
    }

    @Test
    fun `setAdPersonalisationEnabled should change setting on preferences manager`() {
        // WHEN
        repository.setAdPersonalisationEnabled(isEnabled = true)

        // THEN
        verify { mockPreferencesManager.putBoolean(KEY_AD_PERSONALISATION, value = true) }
    }

    @Test
    fun `setAdPersonalisationEnabled should change setting on analytics`() {
        // WHEN
        repository.setAdPersonalisationEnabled(isEnabled = true)

        // THEN
        verify { mockAnalytics.setAdPersonalisationEnabled(isEnabled = true) }
    }

    @Test
    fun `isAdPersonalisationEnabled should get setting from preferences manager`() {
        // GIVEN
        every {
            mockPreferencesManager.getBoolean(KEY_AD_PERSONALISATION, defaultValue = false)
        } returns true

        // WHEN
        val actual = repository.isAdPersonalisationEnabled()

        // THEN
        assertThat(actual).isTrue()
    }

    @Test
    fun `setAnalyticsEnabled should change setting on preferences manager`() {
        // WHEN
        repository.setAnalyticsEnabled(isEnabled = true)

        // THEN
        verify { mockPreferencesManager.putBoolean(KEY_ANALYTICS, value = true) }
    }

    @Test
    fun `setAnalyticsEnabled should change setting on analytics`() {
        // WHEN
        repository.setAnalyticsEnabled(isEnabled = true)

        // THEN
        verify { mockAnalytics.setAnalyticsEnabled(isEnabled = true) }
    }

    @Test
    fun `isAnalyticsEnabled should get setting from preferences manager`() {
        // GIVEN
        every {
            mockPreferencesManager.getBoolean(KEY_ANALYTICS, defaultValue = true)
        } returns false

        // WHEN
        val actual = repository.isAnalyticsEnabled()

        // THEN
        assertThat(actual).isFalse()
    }
}
