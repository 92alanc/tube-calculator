package com.alancamargo.tubecalculator.search.ui.viewmodel

import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal data class SearchViewState(
    val isLoadingOriginSearchResults: Boolean = false,
    val originSearchResults: List<UiStation>? = null,
    val showOriginEmptyState: Boolean = false,
    val isLoadingDestinationSearchResults: Boolean = false,
    val destinationSearchResults: List<UiStation>? = null,
    val showDestinationEmptyState: Boolean = false,
    val busAndTramJourneyCount: Int = 0
) {

    fun onLoadingOriginSearchResults() = copy(
        isLoadingOriginSearchResults = true,
        showOriginEmptyState = false
    )

    fun onStopLoadingOriginSearchResults() = copy(isLoadingOriginSearchResults = false)

    fun onLoadingDestinationSearchResults() = copy(
        isLoadingDestinationSearchResults = true,
        showDestinationEmptyState = false
    )

    fun onStopLoadingDestinationSearchResults() = copy(isLoadingDestinationSearchResults = false)

    fun onReceivedOriginSearchResults(searchResults: List<UiStation>) = copy(
        originSearchResults = searchResults,
        showOriginEmptyState = false
    )

    fun onOriginEmptyState() = copy(showOriginEmptyState = true)

    fun onReceivedDestinationSearchResults(searchResults: List<UiStation>) = copy(
        destinationSearchResults = searchResults,
        showDestinationEmptyState = false
    )

    fun onDestinationEmptyState() = copy(showDestinationEmptyState = true)

    fun onUpdateBusAndTramJourneyCount(count: Int) = copy(busAndTramJourneyCount = count)
}
