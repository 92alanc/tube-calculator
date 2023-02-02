package com.alancamargo.tubecalculator.settings.domain.usecase.crash

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.IsCrashLoggingEnabledUseCaseImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class IsCrashLoggingEnabledUseCaseImplTest {

    private val mockRepository = mockk<SettingsRepository>()
    private val useCase = IsCrashLoggingEnabledUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get setting from repository`() {
        // GIVEN
        every { mockRepository.isCrashLoggingEnabled() } returns true

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isTrue()
    }
}
