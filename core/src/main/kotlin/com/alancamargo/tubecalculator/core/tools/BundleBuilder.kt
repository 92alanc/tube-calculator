package com.alancamargo.tubecalculator.core.tools

import android.os.Bundle

class BundleBuilder {

    private val keysAndValues = mutableMapOf<String, Any?>()

    infix fun String.withValue(value: Any?): BundleBuilder {
        keysAndValues[this] = value
        return this@BundleBuilder
    }

    fun build(): Bundle {
        val bundle = Bundle()

        keysAndValues.entries.forEach { (key, value) ->
            when (value) {
                is String? -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                else -> throw IllegalArgumentException("BundleBuilder only supports string, int and boolean")
            }
        }

        return bundle
    }
}
