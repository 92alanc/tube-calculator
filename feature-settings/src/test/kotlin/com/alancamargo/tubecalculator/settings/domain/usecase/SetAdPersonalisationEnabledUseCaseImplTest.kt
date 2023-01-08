package com.alancamargo.tubecalculator.settings.domain.usecase

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SetAdPersonalisationEnabledUseCaseImplTest {

    private val mockRepository = mockk<SettingsRepository>(relaxed = true)
    private val useCase = SetAdPersonalisationEnabledUseCaseImpl(mockRepository)

    @Test
    fun `invoke should change setting on repository`() {
        // WHEN
        useCase(isEnabled = true)

        // THEN
        verify { mockRepository.setAdPersonalisationEnabled(isEnabled = true) }
    }
}
