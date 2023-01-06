package com.alancamargo.tubecalculator.settings.ui.navigation

import android.content.Context
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import com.alancamargo.tubecalculator.settings.ui.SettingsActivity
import javax.inject.Inject

internal class SettingsActivityNavigationImpl @Inject constructor() : SettingsActivityNavigation {

    override fun startActivity(context: Context) {
        val intent = SettingsActivity.getIntent(context)
        context.startActivity(intent)
    }
}
