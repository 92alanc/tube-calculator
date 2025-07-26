package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.fares.ui.model.UiFare

internal data class FaresViewState(
    val isLoading: Boolean = false,
    val fares: List<UiFare>? = null,
    val cheapestTotalFare: String? = null
) {

    fun onLoading() = copy(isLoading = true)

    fun onStopLoading() = copy(isLoading = false)

    fun onReceivedRailFares(railFares: List<UiFare.UiRailFare>) = copy(
        fares = railFares + (fares ?: emptyList())
    )

    fun onReceivedBusAndTramFare(busAndTramFare: UiFare.UiBusAndTramFare) = copy(
        fares = fares?.plus(busAndTramFare) ?: listOf(busAndTramFare)
    )

    fun onReceivedCheapestTotalFare(cheapestTotalFare: String) = copy(
        cheapestTotalFare = cheapestTotalFare
    )
}
