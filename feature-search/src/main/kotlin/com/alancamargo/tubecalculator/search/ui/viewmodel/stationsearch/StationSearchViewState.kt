package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal data class StationSearchViewState(
    val isLoading: Boolean = false,
    val searchResults: List<UiStation>? = null,
    val showEmptyState: Boolean = false,
    val selectedStation: UiStation? = null
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

    fun clearQuery() = copy(
        searchResults = null,
        selectedStation = null
    )

    fun onStationSelected(selectedStation: UiStation) = copy(
        selectedStation = selectedStation,
        searchResults = null
    )
}
