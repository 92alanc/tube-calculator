package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

private const val BASE_FARE = 1.65
private const val DAILY_CAP = 4.95

class CalculateBusAndTramFareUseCaseImplTest {

    private val mockRepository = mockk<FaresRepository>()
    private val useCase = CalculateBusAndTramFareUseCaseImpl(mockRepository)

    @Before
    fun setUp() {
        every { mockRepository.getBusAndTramBaseFare() } returns BigDecimal.valueOf(BASE_FARE)
        every { mockRepository.getBusAndTramDailyFareCap() } returns BigDecimal.valueOf(DAILY_CAP)
    }

    @Test
    fun `invoke should get formatted bus and tram fare`() {
        // WHEN
        val actual = useCase(busAndTramJourneyCount = 2)

        // THEN
        val expected = Fare.BusAndTramFare(cost = "3.30")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `if fare is zero invoke should return null`() {
        // WHEN
        val actual = useCase(busAndTramJourneyCount = 0)

        // THEN
        assertThat(actual).isNull()
    }

    @Test
    fun `if fare is greater than daily cap should return daily cap`() {
        // WHEN
        val actual = useCase(busAndTramJourneyCount = 5)

        // THEN
        val expected = Fare.BusAndTramFare(cost = DAILY_CAP.toString())
        assertThat(actual).isEqualTo(expected)
    }
}
