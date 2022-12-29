package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot

internal data class FaresViewState(
    val isLoading: Boolean = false,
    val railFares: List<FareListRoot>? = null,
    val busAndTramFare: String? = null,
    val showOnlyBusAndTramFare: Boolean = false
) {

    fun onLoading() = copy(
        isLoading = true,
        railFares = null
    )

    fun onStopLoading() = copy(isLoading = false)

    fun onReceivedRailFares(railFares: List<FareListRoot>) = copy(
        railFares = railFares,
        showOnlyBusAndTramFare = false
    )

    fun onReceivedBusAndTramFare(busAndTramFare: String) = copy(busAndTramFare = busAndTramFare)

    fun onShowOnlyBusAndTramFare() = copy(showOnlyBusAndTramFare = true)
}
