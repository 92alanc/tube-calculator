package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.testtools.BUS_AND_TRAM_FARE
import com.alancamargo.tubecalculator.fares.testtools.stubBusAndTramFare
import com.alancamargo.tubecalculator.fares.testtools.stubRailFaresWithAlternativeRoute
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CalculateCheapestTotalFareUseCaseImplTest {

    private val useCase = CalculateCheapestTotalFareUseCaseImpl()

    @Test
    fun `with rail and bus and tram fares invoke should return sum of cheapest rail fare and bus and tram fare`() {
        // GIVEN
        val railFares = stubRailFaresWithAlternativeRoute()
        val busAndTramFare = stubBusAndTramFare()
        val fares = railFares + busAndTramFare

        // WHEN
        val actual = useCase(fares)

        // THEN
        val expected = "6.00"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `with only rail fares invoke should return cheapest rail fare`() {
        // GIVEN
        val railFares = stubRailFaresWithAlternativeRoute()

        // WHEN
        val actual = useCase(railFares)

        // THEN
        val expected = "2.70"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `with only bus and tram fare invoke should return bus and tram fare`() {
        // GIVEN
        val busAndTramFare = listOf(stubBusAndTramFare())

        // WHEN
        val actual = useCase(busAndTramFare)

        // THEN
        val expected = BUS_AND_TRAM_FARE
        assertThat(actual).isEqualTo(expected)
    }
}
