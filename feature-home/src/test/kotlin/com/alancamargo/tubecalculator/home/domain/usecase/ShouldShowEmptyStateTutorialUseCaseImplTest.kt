package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class ShouldShowEmptyStateTutorialUseCaseImplTest {

    private val mockPreferencesManager = mockk<PreferencesManager>()
    private val useCase = ShouldShowEmptyStateTutorialUseCaseImpl(mockPreferencesManager)

    @Test
    fun `invoke should get value from preferences manager`() {
        // GIVEN
        every {
            mockPreferencesManager.getBoolean(
                key = "home_should_show_empty_state_tutorial",
                defaultValue = true
            )
        } returns true

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isTrue()
    }
}
