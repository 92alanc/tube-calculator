package com.alancamargo.tubecalculator.navigation

import android.content.Context
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType

interface SearchActivityNavigation {

    fun startActivity(context: Context, journeyType: JourneyType)

    fun startActivity(context: Context, journey: Journey)
}
