package com.alancamargo.tubecalculator.settings.domain.usecase.analytics

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class IsAnalyticsEnabledUseCaseImplTest {

    private val mockRepository = mockk<SettingsRepository>()
    private val useCase = IsAnalyticsEnabledUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get setting from repository`() {
        // GIVEN
        every { mockRepository.isAnalyticsEnabled() } returns true

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isTrue()
    }
}
