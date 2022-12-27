package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot

internal data class FaresViewState(
    val isLoading: Boolean = false,
    val showEmptyState: Boolean = false,
    val railFares: FareListRoot? = null,
    val busAndTramFare: String? = null
) {

    fun onLoading() = copy(
        isLoading = true,
        showEmptyState = false,
        railFares = null
    )

    fun onStopLoading() = copy(isLoading = false)

    fun onEmptyState() = copy(
        showEmptyState = true,
        railFares = null
    )

    fun onReceivedRailFares(railFares: FareListRoot) = copy(
        railFares = railFares,
        showEmptyState = false
    )

    fun onReceivedBusAndTramFare(busAndTramFare: String) = copy(busAndTramFare = busAndTramFare)
}
