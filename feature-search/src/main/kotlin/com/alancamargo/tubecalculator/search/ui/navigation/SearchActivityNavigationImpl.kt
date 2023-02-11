package com.alancamargo.tubecalculator.search.ui.navigation

import android.content.Context
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.search.ui.SearchActivity
import javax.inject.Inject

internal class SearchActivityNavigationImpl @Inject constructor(
) : SearchActivityNavigation {

    override fun startActivity(context: Context, journeyType: JourneyType) {
        val intent = SearchActivity.getIntent(context, journeyType)
        context.startActivity(intent)
    }

    override fun startActivity(context: Context, journey: Journey) {
        val intent = SearchActivity.getIntent(context, journey)
        context.startActivity(intent)
    }
}
