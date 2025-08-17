package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError

internal sealed class SearchViewAction {

    data class ShowErrorDialogue(val error: UiSearchError) : SearchViewAction()

    object Finish : SearchViewAction()

    data class SendJourney(val journey: Journey) : SearchViewAction()
}
