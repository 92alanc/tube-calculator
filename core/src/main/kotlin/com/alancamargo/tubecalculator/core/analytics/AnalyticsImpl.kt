package com.alancamargo.tubecalculator.core.analytics

import com.alancamargo.tubecalculator.core.tools.BundleBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject

private const val EVENT_BUTTON_CLICKED = "button_clicked"

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
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }

    override fun trackButtonClicked(
        buttonName: String,
        screenName: String,
        properties: (BundleBuilder.() -> Unit)?
    ) {
        trackEvent(EVENT_BUTTON_CLICKED, screenName, properties)
    }

    override fun trackEvent(
        eventName: String,
        screenName: String,
        properties: (BundleBuilder.() -> Unit)?
    ) {
        val params = properties?.let {
            BundleBuilder().apply(it)
        }?.build()?.apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }

        firebaseAnalytics.logEvent(eventName, params)
    }
}
