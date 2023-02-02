package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.fares.domain.model.Fare

internal data class FaresViewState(
    val isLoading: Boolean = false,
    val fares: List<Fare>? = null,
    val cheapestTotalFare: String? = null
) {

    fun onLoading() = copy(isLoading = true)

    fun onStopLoading() = copy(isLoading = false)

    fun onReceivedRailFares(railFares: List<Fare.RailFare>) = copy(
        fares = railFares + (fares ?: emptyList())
    )

    fun onReceivedBusAndTramFare(busAndTramFare: Fare.BusAndTramFare) = copy(
        fares = fares?.plus(busAndTramFare) ?: listOf(busAndTramFare)
    )

    fun onReceivedCheapestTotalFare(cheapestTotalFare: String) = copy(
        cheapestTotalFare = cheapestTotalFare
    )
}
