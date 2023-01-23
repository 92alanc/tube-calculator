package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal data class StationSearchViewState(val searchResults: List<UiStation>? = null) {

    fun onReceivedSearchResults(searchResults: List<UiStation>) = copy(
        searchResults = searchResults
    )
}
