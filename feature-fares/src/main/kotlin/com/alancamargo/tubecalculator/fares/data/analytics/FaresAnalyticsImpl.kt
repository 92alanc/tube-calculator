package com.alancamargo.tubecalculator.fares.data.analytics

import com.alancamargo.tubecalculator.core.analytics.Analytics
import javax.inject.Inject

private const val SCREEN_NAME = "fares"

private const val BUTTON_NEW_SEARCH = "new_search"
private const val BUTTON_MESSAGES = "messages"

internal class FaresAnalyticsImpl @Inject constructor(
    private val analytics: Analytics
) : FaresAnalytics {

    override fun trackScreenViewed() {
        analytics.trackScreenViewed(SCREEN_NAME)
    }

    override fun trackNewSearchClicked() {
        analytics.trackButtonClicked(
            buttonName = BUTTON_NEW_SEARCH,
            screenName = SCREEN_NAME
        )
    }

    override fun trackMessagesClicked() {
        analytics.trackButtonClicked(
            buttonName = BUTTON_MESSAGES,
            screenName = SCREEN_NAME
        )
    }
}
