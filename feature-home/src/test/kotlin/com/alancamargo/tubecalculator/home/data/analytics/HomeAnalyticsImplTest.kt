package com.alancamargo.tubecalculator.home.data.analytics

import com.alancamargo.tubecalculator.core.analytics.Analytics
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val SCREEN_NAME = "home"

class HomeAnalyticsImplTest {

    private val mockAnalytics = mockk<Analytics>(relaxed = true)
    private val homeAnalytics = HomeAnalyticsImpl(mockAnalytics)

    @Test
    fun `trackScreenViewed should track screen view event`() {
        // WHEN
        homeAnalytics.trackScreenViewed()

        // THEN
        verify { mockAnalytics.trackScreenViewed(SCREEN_NAME) }
    }

    @Test
    fun `trackAddRailJourneyClicked should track button click event`() {
        // WHEN
        homeAnalytics.trackAddRailJourneyClicked()

        // THEN
        verify {
            mockAnalytics.trackButtonClicked(
                buttonName = "add_rail_journey",
                screenName = SCREEN_NAME
            )
        }
    }

    @Test
    fun `trackAddBusAndTramJourneyClicked should track button click event`() {
        // WHEN
        homeAnalytics.trackAddBusAndTramJourneyClicked()

        // THEN
        verify {
            mockAnalytics.trackButtonClicked(
                buttonName = "add_bus_and_tram_journey",
                screenName = SCREEN_NAME
            )
        }
    }

    @Test
    fun `trackCalculateClicked should track button click event`() {
        // WHEN
        homeAnalytics.trackCalculateClicked()

        // THEN
        verify {
            mockAnalytics.trackButtonClicked(
                buttonName = "calculate",
                screenName = SCREEN_NAME
            )
        }
    }

    @Test
    fun `trackJourneyRemoved should track event`() {
        // WHEN
        homeAnalytics.trackJourneyRemoved()

        // THEN
        verify {
            mockAnalytics.trackEvent(
                eventName = "remove_journey",
                screenName = SCREEN_NAME
            )
        }
    }
}
