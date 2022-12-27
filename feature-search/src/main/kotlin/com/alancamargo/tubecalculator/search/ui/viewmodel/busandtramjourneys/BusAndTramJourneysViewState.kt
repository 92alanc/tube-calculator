package com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys

internal data class BusAndTramJourneysViewState(val busAndTramJourneyCount: Int = 0) {

    fun onUpdateBusAndTramJourneyCount(count: Int) = copy(busAndTramJourneyCount = count)
}
