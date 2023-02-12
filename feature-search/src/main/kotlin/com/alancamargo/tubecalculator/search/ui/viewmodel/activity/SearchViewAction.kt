package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError

internal sealed class SearchViewAction {

    object AttachBlankRailJourneyFragments : SearchViewAction()

    data class AttachPreFilledRailJourneyFragments(
        val journey: Journey.Rail
    ) : SearchViewAction()

    object AttachBlankBusAndTramJourneyFragment : SearchViewAction()

    data class AttachPreFilledBusAndTramJourneyFragment(
        val journey: Journey.BusAndTram
    ) : SearchViewAction()

    data class ShowErrorDialogue(val error: UiSearchError) : SearchViewAction()
}
