package com.alancamargo.tubecalculator.navigation

import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType

interface SearchActivityNavigation {

    fun startActivityForResult(
        activity: AppCompatActivity,
        journeyType: JourneyType,
        onResult: (ActivityResult) -> Unit
    )

    fun startActivityForResult(
        activity: AppCompatActivity,
        journey: Journey,
        onResult: (ActivityResult) -> Unit
    )
}
