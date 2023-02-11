package com.alancamargo.tubecalculator.home.data.analytics

import com.alancamargo.tubecalculator.core.analytics.Analytics
import javax.inject.Inject

private const val SCREEN_NAME = "home"

private const val BUTTON_ADD_RAIL_JOURNEY = "add_rail_journey"
private const val BUTTON_ADD_BUS_AND_TRAM_JOURNEY = "add_bus_and_tram_journey"
private const val BUTTON_CALCULATE = "calculate"

private const val EVENT_REMOVE_JOURNEY = "remove_journey"

internal class HomeAnalyticsImpl @Inject constructor(
    private val analytics: Analytics
) : HomeAnalytics {

    override fun trackScreenViewed() {
        analytics.trackScreenViewed(SCREEN_NAME)
    }

    override fun trackAddRailJourneyClicked() {
        analytics.trackButtonClicked(
            buttonName = BUTTON_ADD_RAIL_JOURNEY,
            screenName = SCREEN_NAME
        )
    }

    override fun trackAddBusAndTramJourneyClicked() {
        analytics.trackButtonClicked(
            buttonName = BUTTON_ADD_BUS_AND_TRAM_JOURNEY,
            screenName = SCREEN_NAME
        )
    }

    override fun trackCalculateClicked() {
        analytics.trackButtonClicked(
            buttonName = BUTTON_CALCULATE,
            screenName = SCREEN_NAME
        )
    }

    override fun trackJourneyRemoved() {
        analytics.trackEvent(
            eventName = EVENT_REMOVE_JOURNEY,
            screenName = SCREEN_NAME
        )
    }
}
