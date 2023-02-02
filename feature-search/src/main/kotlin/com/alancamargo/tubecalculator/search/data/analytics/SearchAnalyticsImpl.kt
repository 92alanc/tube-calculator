package com.alancamargo.tubecalculator.search.data.analytics

import com.alancamargo.tubecalculator.core.analytics.Analytics
import javax.inject.Inject

private const val SCREEN_NAME = "search"

private const val BUTTON_CALCULATE = "calculate"
private const val BUTTON_SETTINGS = "settings"
private const val BUTTON_PRIVACY_POLICY = "privacy_policy"
private const val BUTTON_APP_INFO = "app_info"

private const val PROPERTY_ORIGIN = "origin"
private const val PROPERTY_DESTINATION = "destination"
private const val PROPERTY_BUS_AND_TRAM_FARE_COUNT = "bus_and_tram_fare_count"

internal class SearchAnalyticsImpl @Inject constructor(
    private val analytics: Analytics
) : SearchAnalytics {

    override fun trackScreenViewed() {
        analytics.trackScreenViewed(SCREEN_NAME)
    }

    override fun trackCalculateClicked(
        origin: String?,
        destination: String?,
        busAndTramJourneyCount: Int
    ) {
        analytics.trackButtonClicked(
            buttonName = BUTTON_CALCULATE,
            screenName = SCREEN_NAME
        ) {
            PROPERTY_ORIGIN withValue origin
            PROPERTY_DESTINATION withValue destination
            PROPERTY_BUS_AND_TRAM_FARE_COUNT withValue busAndTramJourneyCount
        }
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
}
