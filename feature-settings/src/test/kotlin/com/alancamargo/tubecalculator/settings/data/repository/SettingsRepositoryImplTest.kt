package com.alancamargo.tubecalculator.settings.data.repository

import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val KEY_CRASH_LOGGING = "is_crash_logging_enabled"

class SettingsRepositoryImplTest {

    private val mockPreferencesManager = mockk<PreferencesManager>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val repository = SettingsRepositoryImpl(mockPreferencesManager, mockLogger)

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
            mockPreferencesManager.getBoolean(KEY_CRASH_LOGGING, defaultValue = false)
        } returns true

        // WHEN
        val actual = repository.isCrashLoggingEnabled()

        // THEN
        assertThat(actual).isTrue()
    }
}
