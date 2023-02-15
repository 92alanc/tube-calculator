package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.search.ui.model.SearchType

internal data class StationSearchViewState(
    val stations: List<UiStation>? = null,
    val selectedStation: UiStation? = null,
    @StringRes val labelRes: Int? = null,
    @StringRes val hintRes: Int? = null
) {

    fun onReceivedStations(stations: List<UiStation>) = copy(stations = stations)

    fun onStationSelected(station: UiStation?) = copy(selectedStation = station)

    fun onReceivedSearchType(searchType: SearchType) = copy(
        labelRes = searchType.labelRes,
        hintRes = searchType.hintRes
    )
}
