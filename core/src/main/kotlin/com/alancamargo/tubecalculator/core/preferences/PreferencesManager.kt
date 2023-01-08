package com.alancamargo.tubecalculator.core.preferences

interface PreferencesManager {

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun putBoolean(key: String, value: Boolean)

    fun getInt(key: String, defaultValue: Int): Int

    fun putInt(key: String, value: Int)
}
