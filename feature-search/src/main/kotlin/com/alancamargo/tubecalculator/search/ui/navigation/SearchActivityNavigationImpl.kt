package com.alancamargo.tubecalculator.search.ui.navigation

import android.content.Context
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.search.ui.SearchActivity
import javax.inject.Inject

internal class SearchActivityNavigationImpl @Inject constructor(
) : SearchActivityNavigation {

    override fun startActivity(context: Context) {
        val intent = SearchActivity.getIntent(context)
        context.startActivity(intent)
    }
}
