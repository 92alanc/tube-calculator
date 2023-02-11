package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val KEY = "is_first_access"

class DisableFirstAccessUseCaseImplTest {

    private val mockPreferencesManager = mockk<PreferencesManager>(relaxed = true)
    private val useCase = DisableFirstAccessUseCaseImpl(mockPreferencesManager)

    @Test
    fun `invoke should disable first access on preferences manager`() {
        // WHEN
        useCase()

        // THEN
        verify { mockPreferencesManager.putBoolean(KEY, value = false) }
    }
}