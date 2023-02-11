package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.search.ui.model.UiSearchError

internal sealed class SearchViewAction {

    object AttachRailJourneyFragments : SearchViewAction()

    object AttachBusAndTramJourneyFragment : SearchViewAction()

    data class ShowErrorDialogue(val error: UiSearchError) : SearchViewAction()
}
