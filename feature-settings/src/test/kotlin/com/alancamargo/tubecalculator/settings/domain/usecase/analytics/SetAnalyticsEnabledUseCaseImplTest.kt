package com.alancamargo.tubecalculator.settings.domain.usecase.analytics

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SetAnalyticsEnabledUseCaseImplTest {

    private val mockRepository = mockk<SettingsRepository>(relaxed = true)
    private val useCase = SetAnalyticsEnabledUseCaseImpl(mockRepository)

    @Test
    fun `invoke should change setting on repository`() {
        // WHEN
        useCase(isEnabled = true)

        // THEN
        verify { mockRepository.setAnalyticsEnabled(isEnabled = true) }
    }
}
