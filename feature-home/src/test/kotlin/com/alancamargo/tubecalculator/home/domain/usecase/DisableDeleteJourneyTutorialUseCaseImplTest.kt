package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class DisableDeleteJourneyTutorialUseCaseImplTest {

    private val mockPreferencesManager = mockk<PreferencesManager>(relaxed = true)
    private val useCase = DisableDeleteJourneyTutorialUseCaseImpl(mockPreferencesManager)

    @Test
    fun `invoke should disable delete journey tutorial on preferences manager`() {
        // WHEN
        useCase()

        // THEN
        verify {
            mockPreferencesManager.putBoolean(
                key = "home_should_show_delete_journey_tutorial",
                value = false
            )
        }
    }
}
