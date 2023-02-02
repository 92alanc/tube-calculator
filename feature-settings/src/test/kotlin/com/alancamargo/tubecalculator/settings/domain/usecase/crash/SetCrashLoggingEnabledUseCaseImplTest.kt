package com.alancamargo.tubecalculator.settings.domain.usecase.crash

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SetCrashLoggingEnabledUseCaseImplTest {

    private val mockRepository = mockk<SettingsRepository>(relaxed = true)
    private val useCase = SetCrashLoggingEnabledUseCaseImpl(mockRepository)

    @Test
    fun `invoke should change setting on repository`() {
        // WHEN
        useCase(isEnabled = true)

        // THEN
        verify { mockRepository.setCrashLoggingEnabled(isEnabled = true) }
    }
}
