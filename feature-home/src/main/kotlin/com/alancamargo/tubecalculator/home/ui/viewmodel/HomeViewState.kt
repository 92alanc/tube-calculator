package com.alancamargo.tubecalculator.home.ui.viewmodel

import com.alancamargo.tubecalculator.common.ui.model.Journey

internal data class HomeViewState(
    val journeys: List<Journey>? = null,
    val showAddButton: Boolean = true,
    val showCalculateButton: Boolean = false
) {

    fun onJourneysUpdated(journeys: List<Journey>): HomeViewState {
        val areJourneysFull = journeys.any {
            it is Journey.Rail
        } && journeys.any {
            it is Journey.BusAndTram
        }

        return copy(
            journeys = journeys,
            showCalculateButton = journeys.isNotEmpty(),
            showAddButton = !areJourneysFull
        )
    }
}
