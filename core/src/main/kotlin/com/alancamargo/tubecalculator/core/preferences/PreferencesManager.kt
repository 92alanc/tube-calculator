package com.alancamargo.tubecalculator.core.preferences

interface PreferencesManager {

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun putBoolean(key: String, value: Boolean)
}
