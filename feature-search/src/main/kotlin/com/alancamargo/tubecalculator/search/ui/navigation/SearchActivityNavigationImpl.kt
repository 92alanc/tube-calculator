package com.alancamargo.tubecalculator.search.ui.navigation

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.search.ui.SearchActivity
import javax.inject.Inject

internal class SearchActivityNavigationImpl @Inject constructor(
) : SearchActivityNavigation {

    override fun startActivityForResult(
        activity: AppCompatActivity,
        journeyType: JourneyType,
        onResult: (ActivityResult) -> Unit
    ) {
        val intent = SearchActivity.getIntent(activity, journeyType)
        activity.startActivityForResult(intent, onResult)
    }

    override fun startActivityForResult(
        activity: AppCompatActivity,
        journey: Journey,
        onResult: (ActivityResult) -> Unit
    ) {
        val intent = SearchActivity.getIntent(activity, journey)
        activity.startActivityForResult(intent, onResult)
    }

    private fun AppCompatActivity.startActivityForResult(
        intent: Intent,
        onResult: (ActivityResult) -> Unit
    ) {
        val request = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            onResult(it)
        }

        request.launch(intent)
    }
}
