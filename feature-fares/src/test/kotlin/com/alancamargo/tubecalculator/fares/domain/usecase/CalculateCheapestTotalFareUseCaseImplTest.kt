package com.alancamargo.tubecalculator.fares.domain.usecase

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

        // WHEN
        val actual = useCase(railFares, busAndTramFare)

        // THEN
        val expected = "6.00"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `with only rail fares invoke should return cheapest rail fare`() {
        // GIVEN
        val railFares = stubRailFaresWithAlternativeRoute()

        // WHEN
        val actual = useCase(railFares, busAndTramFare = null)

        // THEN
        val expected = "2.70"
        assertThat(actual).isEqualTo(expected)
    }
}
