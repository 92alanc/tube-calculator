package com.alancamargo.tubecalculator.search.data.analytics

import com.alancamargo.tubecalculator.core.analytics.Analytics
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SearchAnalyticsImplTest {

    private val mockAnalytics = mockk<Analytics>(relaxed = true)
    private val searchAnalytics = SearchAnalyticsImpl(mockAnalytics)

    @Test
    fun `trackScreenViewed should track screen view event`() {
        // WHEN
        searchAnalytics.trackScreenViewed()

        // THEN
        verify { mockAnalytics.trackScreenViewed(screenName = "search") }
    }

    @Test
    fun `trackCalculateClicked should track button click event`() {
        // GIVEN
        val origin = "Romford"
        val destination = "Blackfriars"
        val busAndTramJourneyCount = 1

        // WHEN
        searchAnalytics.trackCalculateClicked(origin, destination, busAndTramJourneyCount)

        // THEN
        verify { mockAnalytics.trackButtonClicked(buttonName = "calculate", properties = any()) }
    }

    @Test
    fun `trackSettingsClicked should track button click event`() {
        // WHEN
        searchAnalytics.trackSettingsClicked()

        // THEN
        verify { mockAnalytics.trackButtonClicked(buttonName = "settings") }
    }

    @Test
    fun `trackAppInfoClicked should track button click event`() {
        // WHEN
        searchAnalytics.trackAppInfoClicked()

        // THEN
        verify { mockAnalytics.trackButtonClicked(buttonName = "app_info") }
    }
}
