package com.alancamargo.tubecalculator.core.analytics

import com.alancamargo.tubecalculator.core.tools.BundleBuilder

interface Analytics {

    fun setAnalyticsEnabled(isEnabled: Boolean)

    fun setAdPersonalisationEnabled(isEnabled: Boolean)

    fun trackScreenViewed(screenName: String)

    fun trackButtonClicked(buttonName: String, properties: (BundleBuilder.() -> Unit)? = null)

    fun trackEvent(eventName: String, properties: (BundleBuilder.() -> Unit)? = null)
}
