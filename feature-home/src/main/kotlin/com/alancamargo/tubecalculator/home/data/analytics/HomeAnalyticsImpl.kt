package com.alancamargo.tubecalculator.home.data.analytics

import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.core.analytics.Analytics
import javax.inject.Inject

private const val SCREEN_NAME = "home"

private const val BUTTON_ADD_RAIL_JOURNEY = "add_rail_journey"
private const val BUTTON_ADD_BUS_AND_TRAM_JOURNEY = "add_bus_and_tram_journey"
private const val BUTTON_CALCULATE = "calculate"
private const val BUTTON_SETTINGS = "settings"
private const val BUTTON_PRIVACY_POLICY = "privacy_policy"
private const val BUTTON_APP_INFO = "app_info"

private const val EVENT_REMOVE_JOURNEY = "remove_journey"
private const val EVENT_EDIT_JOURNEY = "edit_journey"

private const val PROPERTY_HAS_RAIL_JOURNEYS = "has_rail_journeys"
private const val PROPERTY_HAS_BUS_AND_TRAM_JOURNEYS = "has_bus_and_tram_journeys"

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

    override fun trackCalculateClicked(journeys: List<Journey>) {
        val hasRailJourneys = journeys.any { it is Journey.Rail }
        val hasBusAndTramJourneys = journeys.any { it is Journey.BusAndTram }

        analytics.trackButtonClicked(
            buttonName = BUTTON_CALCULATE,
            screenName = SCREEN_NAME
        ) {
            PROPERTY_HAS_RAIL_JOURNEYS withValue hasRailJourneys
            PROPERTY_HAS_BUS_AND_TRAM_JOURNEYS withValue hasBusAndTramJourneys
        }
    }

    override fun trackJourneyRemoved() {
        analytics.trackEvent(
            eventName = EVENT_REMOVE_JOURNEY,
            screenName = SCREEN_NAME
        )
    }

    override fun trackSettingsClicked() {
        analytics.trackButtonClicked(
            buttonName = BUTTON_SETTINGS,
            screenName = SCREEN_NAME
        )
    }

    override fun trackPrivacyPolicyClicked() {
        analytics.trackButtonClicked(
            buttonName = BUTTON_PRIVACY_POLICY,
            screenName = SCREEN_NAME
        )
    }

    override fun trackAppInfoClicked() {
        analytics.trackButtonClicked(
            buttonName = BUTTON_APP_INFO,
            screenName = SCREEN_NAME
        )
    }

    override fun trackJourneyClicked() {
        analytics.trackEvent(
            eventName = EVENT_EDIT_JOURNEY,
            screenName = SCREEN_NAME
        )
    }
}
