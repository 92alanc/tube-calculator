package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError

internal sealed class SearchViewAction {

    object AttachFragments : SearchViewAction()

    data class NavigateToFares(
        val origin: UiStation?,
        val destination: UiStation?,
        val busAndTramJourneyCount: Int
    ) : SearchViewAction()

    data class ShowErrorDialogue(val error: UiSearchError) : SearchViewAction()

    data class ShowAppInfo(val appVersionName: String) : SearchViewAction()

    object NavigateToSettings : SearchViewAction()

    object ShowPrivacyPolicyDialogue : SearchViewAction()

    object ShowFirstAccessDialogue : SearchViewAction()
}
