package com.alancamargo.tubecalculator.core.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val SCREEN_NAME = "fares"

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
    fun `trackScreenViewed should track screen view event on firebase`() {
        // WHEN
        val screenName = "fares"
        analytics.trackScreenViewed(screenName)

        // THEN
        verify { mockFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, any()) }
    }

    @Test
    fun `trackButtonClicked should track button click event on firebase`() {
        // WHEN
        val buttonName = "calculate"
        analytics.trackButtonClicked(buttonName, SCREEN_NAME)

        // THEN
        val eventName = "button_clicked"
        verify { mockFirebaseAnalytics.logEvent(eventName, any()) }
    }
}
