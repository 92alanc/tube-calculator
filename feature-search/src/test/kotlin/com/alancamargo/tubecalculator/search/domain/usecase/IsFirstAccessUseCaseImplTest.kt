package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

private const val KEY = "is_first_access"

class IsFirstAccessUseCaseImplTest {

    private val mockPreferencesManager = mockk<PreferencesManager>()
    private val useCase = IsFirstAccessUseCaseImpl(mockPreferencesManager)

    @Test
    fun `invoke should get value from preferences manager`() {
        // GIVEN
        every { mockPreferencesManager.getBoolean(KEY, defaultValue = true) } returns true

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isTrue()
    }
}
