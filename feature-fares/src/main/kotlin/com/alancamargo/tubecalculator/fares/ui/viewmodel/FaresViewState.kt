package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.fares.domain.model.FareRoot

internal data class FaresViewState(
    val isLoading: Boolean = false,
    val fares: List<FareRoot>? = null
) {

    fun onLoading() = copy(isLoading = true)

    fun onStopLoading() = copy(isLoading = false)

    fun onReceivedRailFares(railFares: List<FareRoot.RailFare>) = copy(
        fares = fares?.plus(railFares) ?: railFares
    )

    fun onReceivedBusAndTramFare(busAndTramFare: FareRoot.BusAndTramFare) = copy(
        fares = fares?.plus(busAndTramFare) ?: listOf(busAndTramFare)
    )
}
