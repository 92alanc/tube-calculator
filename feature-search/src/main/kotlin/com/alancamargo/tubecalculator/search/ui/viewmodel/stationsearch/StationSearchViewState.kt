package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal data class StationSearchViewState(
    val stations: List<UiStation>? = null,
    val minQueryLength: Int? = null
) {

    fun onReceivedStations(stations: List<UiStation>) = copy(stations = stations)

    fun onReceivedMinQueryLength(minQueryLength: Int) = copy(minQueryLength = minQueryLength)
}
