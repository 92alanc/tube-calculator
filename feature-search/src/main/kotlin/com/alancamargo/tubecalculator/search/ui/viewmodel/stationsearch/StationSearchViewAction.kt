package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.search.ui.model.UiSearchError

internal sealed class StationSearchViewAction {

    data class ShowErrorDialogue(val error: UiSearchError) : StationSearchViewAction()
}
