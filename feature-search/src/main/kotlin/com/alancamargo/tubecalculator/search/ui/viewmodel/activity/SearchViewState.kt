package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

internal data class SearchViewState(
    val busAndTramJourneyCount: Int = 0
) {

    fun onUpdateBusAndTramJourneyCount(count: Int) = copy(busAndTramJourneyCount = count)
}
