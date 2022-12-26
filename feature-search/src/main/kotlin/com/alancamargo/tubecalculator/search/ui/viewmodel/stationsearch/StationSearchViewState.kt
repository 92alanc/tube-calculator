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
        showEmptyState = false
    )

    fun onStopLoading() = copy(isLoading = false)

    fun onReceivedSearchResults(searchResults: List<UiStation>) = copy(
        searchResults = searchResults,
        showEmptyState = false
    )

    fun onEmptyState() = copy(showEmptyState = true)

    fun onSelectedStation(selectedStation: UiStation) = copy(selectedStation = selectedStation)
}
