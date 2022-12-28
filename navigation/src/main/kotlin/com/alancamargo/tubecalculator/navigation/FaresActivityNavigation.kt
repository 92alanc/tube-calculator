package com.alancamargo.tubecalculator.navigation

import android.content.Context
import com.alancamargo.tubecalculator.common.ui.model.UiStation

interface FaresActivityNavigation {

    fun startActivity(
        context: Context,
        origin: UiStation?,
        destination: UiStation?,
        busAndTramJourneyCount: Int
    )
}
