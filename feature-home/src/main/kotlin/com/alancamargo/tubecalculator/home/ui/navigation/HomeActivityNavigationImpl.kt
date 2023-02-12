package com.alancamargo.tubecalculator.home.ui.navigation

import android.content.Context
import com.alancamargo.tubecalculator.home.ui.HomeActivity
import com.alancamargo.tubecalculator.navigation.HomeActivityNavigation
import javax.inject.Inject

internal class HomeActivityNavigationImpl @Inject constructor() : HomeActivityNavigation {

    override fun startActivity(context: Context) {
        val intent = HomeActivity.getIntent(context)
        context.startActivity(intent)
    }
}
