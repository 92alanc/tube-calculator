package com.alancamargo.tubecalculator.settings.domain.usecase.ads

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.IsAdPersonalisationEnabledUseCaseImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class IsAdPersonalisationEnabledUseCaseImplTest {

    private val mockRepository = mockk<SettingsRepository>()
    private val useCase = IsAdPersonalisationEnabledUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get setting from repository`() {
        // GIVEN
        every { mockRepository.isAdPersonalisationEnabled() } returns true

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isTrue()
    }
}
