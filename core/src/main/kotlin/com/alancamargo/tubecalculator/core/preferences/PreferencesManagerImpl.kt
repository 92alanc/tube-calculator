package com.alancamargo.tubecalculator.core.preferences

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

private const val FILE_NAME = "tube_calculator_preferences"

internal class PreferencesManagerImpl @Inject constructor(
    private val context: Context
) : PreferencesManager {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun putInt(key: String, value: Int) {
        sharedPreferences.edit { putInt(key, value) }
    }
}
