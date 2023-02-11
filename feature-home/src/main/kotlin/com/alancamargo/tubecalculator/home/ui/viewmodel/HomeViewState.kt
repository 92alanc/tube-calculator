package com.alancamargo.tubecalculator.home.ui.viewmodel

import com.alancamargo.tubecalculator.common.ui.model.Journey

internal data class HomeViewState(
    val journeys: List<Journey>? = null,
    val showAddButton: Boolean = true,
    val showCalculateButton: Boolean = false
) {

    fun onJourneyReceived(journey: Journey) = copy(
        journeys = journeys?.plus(journey) ?: listOf(journey)
    )

    fun onJourneyRemoved(journey: Journey) = copy(
        journeys = journeys?.minus(journey) ?: emptyList()
    )

    fun onShowAddButton() = copy(showAddButton = true)

    fun onHideAddButton() = copy(showAddButton = false)

    fun onShowCalculateButton() = copy(showCalculateButton = true)

    fun onHideCalculateButton() = copy(showCalculateButton = false)
}
