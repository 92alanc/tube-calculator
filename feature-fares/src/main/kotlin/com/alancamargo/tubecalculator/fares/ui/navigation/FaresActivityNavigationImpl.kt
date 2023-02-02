package com.alancamargo.tubecalculator.fares.ui.navigation

import android.content.Context
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.fares.ui.FaresActivity
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import javax.inject.Inject

internal class FaresActivityNavigationImpl @Inject constructor() : FaresActivityNavigation {

    override fun startActivity(
        context: Context,
        origin: UiStation?,
        destination: UiStation?,
        busAndTramJourneyCount: Int
    ) {
        val intent = FaresActivity.getIntent(
            context = context,
            origin = origin,
            destination = destination,
            busAndTramJourneyCount = busAndTramJourneyCount
        )
        context.startActivity(intent)
    }
}
