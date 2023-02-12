package com.alancamargo.tubecalculator.search.ui.navigation

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.search.ui.SearchActivity
import javax.inject.Inject

internal class SearchActivityNavigationImpl @Inject constructor(
) : SearchActivityNavigation {

    override fun startActivityForResult(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        journeyType: JourneyType,
        onResult: (ActivityResult) -> Unit
    ) {
        val intent = SearchActivity.getIntent(context, journeyType)
        launcher.launch(intent)
    }

    override fun startActivityForResult(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        journey: Journey,
        onResult: (ActivityResult) -> Unit
    ) {
        val intent = SearchActivity.getIntent(context, journey)
        launcher.launch(intent)
    }
}
