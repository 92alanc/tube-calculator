package com.alancamargo.tubecalculator.core.analytics

import android.os.Bundle

fun properties(block: AnalyticsPropertiesBuilder.() -> Unit): AnalyticsPropertiesBuilder {
    return AnalyticsPropertiesBuilder().apply(block)
}

data class AnalyticsPropertiesBuilder internal constructor(
    private val propertiesMap: MutableMap<String, Any?> = mutableMapOf()
) {

    infix fun String.withValue(value: Any?): AnalyticsPropertiesBuilder {
        propertiesMap[this] = value
        return this@AnalyticsPropertiesBuilder
    }

    fun buildAsBundle(): Bundle {
        val bundle = Bundle()

        propertiesMap.forEach { (key, value) ->
            if (value is String) {
                bundle.putString(key, value)
            } else if (value is Boolean) {
                bundle.putBoolean(key, value)
            }
        }

        return bundle
    }
}
