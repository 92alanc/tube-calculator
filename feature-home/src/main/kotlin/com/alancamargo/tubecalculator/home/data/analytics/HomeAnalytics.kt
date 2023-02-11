package com.alancamargo.tubecalculator.home.data.analytics

internal interface HomeAnalytics {

    fun trackScreenViewed()

    fun trackAddRailJourneyClicked()

    fun trackAddBusAndTramJourneyClicked()

    fun trackCalculateClicked()

    fun trackJourneyRemoved()
}
