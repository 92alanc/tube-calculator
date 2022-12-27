package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import java.math.BigDecimal

class CalculateBusAndTramFareUseCaseImplTest {

    private val mockRepository = mockk<FaresRepository>()
    private val useCase = CalculateBusAndTramFareUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get formatted bus and tram fare`() {
        // GIVEN
        every { mockRepository.getBusAndTramBaseFare() } returns BigDecimal("1.65")

        // WHEN
        val actual = useCase(busAndTramJourneyCount = 2)

        // THEN
        val expected = "£3.30"
        assertThat(actual).isEqualTo(expected)
    }
}
