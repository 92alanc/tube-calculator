package com.alancamargo.tubecalculator.core.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class AnalyticsImplTest {

    private val mockFirebaseAnalytics = mockk<FirebaseAnalytics>(relaxed = true)
    private val analytics = AnalyticsImpl(mockFirebaseAnalytics)

    @Test
    fun `setAnalyticsEnabled should change setting on firebase`() {
        // WHEN
        analytics.setAnalyticsEnabled(isEnabled = true)

        // THEN
        verify { mockFirebaseAnalytics.setAnalyticsCollectionEnabled(true) }
    }

    @Test
    fun `setAdPersonalisationEnabled should change setting on firebase`() {
        // WHEN
        analytics.setAdPersonalisationEnabled(isEnabled = true)

        // THEN
        verify {
            mockFirebaseAnalytics.setUserProperty(
                FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS,
                true.toString()
            )
        }
    }

    @Test
    fun `trackScreenViewed should track screen view event`() {
        // WHEN
        val screenName = "fares"
        analytics.trackScreenViewed(screenName)

        // THEN
        verify { mockFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, any()) }
    }
}
