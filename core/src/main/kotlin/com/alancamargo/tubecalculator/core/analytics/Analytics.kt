package com.alancamargo.tubecalculator.core.analytics

import android.os.Bundle

interface Analytics {

    fun setAnalyticsEnabled(isEnabled: Boolean)

    fun setAdPersonalisationEnabled(isEnabled: Boolean)

    fun trackScreenViewed(screenName: String)

    fun trackEvent(eventName: String, properties: Bundle? = null)
}
