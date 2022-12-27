package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal data class StationSearchViewState(
    val isLoading: Boolean = false,
    val searchResults: List<UiStation>? = null,
    val showEmptyState: Boolean = false,
    val isSearchButtonEnabled: Boolean = false
) {

    fun onLoading() = copy(
        isLoading = true,
        showEmptyState = false,
        searchResults = null
    )

    fun onStopLoading() = copy(isLoading = false)

    fun onReceivedSearchResults(searchResults: List<UiStation>) = copy(
        searchResults = searchResults,
        showEmptyState = false
    )

    fun onEmptyState() = copy(
        showEmptyState = true,
        searchResults = null
    )

    fun onSelectedStation(selectedStation: UiStation) = copy(
        searchResults = listOf(selectedStation)
    )

    fun enableSearchButton() = copy(isSearchButtonEnabled = true)

    fun disableSearchButton() = copy(isSearchButtonEnabled = false)

    fun clearSearchResults() = copy(searchResults = null)
}
