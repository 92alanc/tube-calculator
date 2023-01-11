package com.alancamargo.tubecalculator.core.analytics

interface Analytics {

    fun setAnalyticsEnabled(isEnabled: Boolean)

    fun setAdPersonalisationEnabled(isEnabled: Boolean)

    fun trackScreenViewed(screenName: String)

    fun trackEvent(eventName: String, properties: AnalyticsPropertiesBuilder? = null)
}
