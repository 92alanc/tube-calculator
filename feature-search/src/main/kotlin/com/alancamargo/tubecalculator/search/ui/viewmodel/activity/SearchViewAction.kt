package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError

internal sealed class SearchViewAction {

    data class NavigateToFares(
        val origin: UiStation,
        val destination: UiStation,
        val busAndTramJourneyCount: Int
    ) : SearchViewAction()

    data class ShowErrorDialogue(val error: UiSearchError) : SearchViewAction()

    object ShowAppInfo : SearchViewAction()
}
