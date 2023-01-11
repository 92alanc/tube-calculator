package com.alancamargo.tubecalculator.fares.data.analytics

internal interface FaresAnalytics {

    fun trackScreenViewed()

    fun trackNewSearchClicked()

    fun trackMessagesClicked()
}
