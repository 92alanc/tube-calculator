package com.alancamargo.tubecalculator.home.ui.viewmodel

import com.alancamargo.tubecalculator.common.ui.model.Journey

internal sealed class HomeViewAction {

    data class NavigateToFares(val journeys: List<Journey>) : HomeViewAction()

    object NavigateToSettings : HomeViewAction()

    object ShowPrivacyPolicyDialogue : HomeViewAction()

    data class ShowAppInfo(val appVersionName: String) : HomeViewAction()

    object ShowFirstAccessDialogue : HomeViewAction()

    data class EditJourney(val journey: Journey) : HomeViewAction()

    object AddRailJourney : HomeViewAction()

    object AddBusAndTramJourney : HomeViewAction()
}
