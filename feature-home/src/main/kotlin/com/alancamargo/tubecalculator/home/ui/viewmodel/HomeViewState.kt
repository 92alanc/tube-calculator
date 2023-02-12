package com.alancamargo.tubecalculator.home.ui.viewmodel

import com.alancamargo.tubecalculator.common.ui.model.Journey

internal data class HomeViewState(
    val journeys: List<Journey>? = null,
    val showAddButton: Boolean = true,
    val showCalculateButton: Boolean = false,
    val isAddButtonExpanded: Boolean = false,
    val showAddBusAndTramJourneyButton: Boolean = false,
    val showAddRailJourneyButton: Boolean = false,
    val showEmptyState: Boolean = true
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
            showAddButton = !areJourneysFull,
            showEmptyState = journeys.isEmpty()
        )
    }

    fun expandAddButton() = copy(
        isAddButtonExpanded = true,
        showAddRailJourneyButton = journeys?.none { it is Journey.Rail } ?: true,
        showAddBusAndTramJourneyButton = journeys?.none { it is Journey.BusAndTram } ?: true,
        showEmptyState = false
    )

    fun collapseAddButton() = copy(
        isAddButtonExpanded = false,
        showAddRailJourneyButton = false,
        showAddBusAndTramJourneyButton = false,
        showEmptyState = journeys.isNullOrEmpty()
    )
}
