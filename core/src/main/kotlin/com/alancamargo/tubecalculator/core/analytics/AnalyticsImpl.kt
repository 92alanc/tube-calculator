package com.alancamargo.tubecalculator.core.analytics

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

internal class AnalyticsImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {

    override fun setAnalyticsEnabled(isEnabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(isEnabled)
    }

    override fun setAdPersonalisationEnabled(isEnabled: Boolean) {
        firebaseAnalytics.setUserProperty(
            FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS,
            isEnabled.toString()
        )
    }

    override fun trackScreenViewed(screenName: String) {
        firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundleOf(FirebaseAnalytics.Param.SCREEN_NAME to screenName)
        )
    }

    override fun trackEvent(eventName: String, properties: AnalyticsPropertiesBuilder?) {
        firebaseAnalytics.logEvent(eventName, properties?.buildAsBundle())
    }
}
