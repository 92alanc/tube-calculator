package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class DisableEmptyStateTutorialUseCaseImplTest {

    private val mockPreferencesManager = mockk<PreferencesManager>(relaxed = true)
    private val useCase = DisableEmptyStateTutorialUseCaseImpl(mockPreferencesManager)

    @Test
    fun `invoke should disable empty state tutorial on preferences manager`() {
        // WHEN
        useCase()

        // THEN
        verify {
            mockPreferencesManager.putBoolean(
                key = "home_should_show_empty_state_tutorial",
                value = false
            )
        }
    }
}
