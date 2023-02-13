package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class ShouldShowDeleteJourneyTutorialUseCaseImplTest {

    private val mockPreferencesManager = mockk<PreferencesManager>()
    private val useCase = ShouldShowDeleteJourneyTutorialUseCaseImpl(mockPreferencesManager)

    @Test
    fun `invoke should get value from preferences manager`() {
        // GIVEN
        every {
            mockPreferencesManager.getBoolean(
                key = "home_should_show_delete_journey_tutorial",
                defaultValue = true
            )
        } returns true

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isTrue()
    }
}
