package com.alancamargo.tubecalculator.home.data.analytics

import com.alancamargo.tubecalculator.common.ui.model.Journey

internal interface HomeAnalytics {

    fun trackScreenViewed()

    fun trackAddRailJourneyClicked()

    fun trackAddBusAndTramJourneyClicked()

    fun trackCalculateClicked(journeys: List<Journey>)

    fun trackJourneyRemoved()

    fun trackSettingsClicked()

    fun trackPrivacyPolicyClicked()

    fun trackAppInfoClicked()

    fun trackJourneyClicked()
}
