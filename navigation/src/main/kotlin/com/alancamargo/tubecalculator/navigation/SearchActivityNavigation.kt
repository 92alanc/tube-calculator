package com.alancamargo.tubecalculator.navigation

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType

interface SearchActivityNavigation {

    fun startActivityForResult(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        journeyType: JourneyType,
        onResult: (ActivityResult) -> Unit
    )

    fun startActivityForResult(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        journey: Journey,
        onResult: (ActivityResult) -> Unit
    )
}
