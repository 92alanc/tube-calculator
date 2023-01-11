package com.alancamargo.tubecalculator.fares.data.analytics

import com.alancamargo.tubecalculator.core.analytics.Analytics
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val SCREEN_NAME = "fares"

class FaresAnalyticsImplTest {

    private val mockAnalytics = mockk<Analytics>(relaxed = true)
    private val faresAnalytics = FaresAnalyticsImpl(mockAnalytics)

    @Test
    fun `trackScreenViewed should track screen view event`() {
        // WHEN
        faresAnalytics.trackScreenViewed()

        // THEN
        verify { mockAnalytics.trackScreenViewed(SCREEN_NAME) }
    }

    @Test
    fun `trackNewSearchClicked should track button click event`() {
        // WHEN
        faresAnalytics.trackNewSearchClicked()

        // THEN
        verify {
            mockAnalytics.trackButtonClicked(
                buttonName = "new_search",
                screenName = SCREEN_NAME,
                properties = null
            )
        }
    }

    @Test
    fun `trackMessagesClicked should track button click event`() {
        // WHEN
        faresAnalytics.trackMessagesClicked()

        // THEN
        verify {
            mockAnalytics.trackButtonClicked(
                buttonName = "messages",
                screenName = SCREEN_NAME,
                properties = null
            )
        }
    }

    @Test
    fun `trackBackClicked should track button click event`() {
        // WHEN
        faresAnalytics.trackBackClicked()

        // THEN
        verify {
            mockAnalytics.trackButtonClicked(
                buttonName = "back",
                screenName = SCREEN_NAME,
                properties = null
            )
        }
    }
}
