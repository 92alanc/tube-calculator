package com.alancamargo.tubecalculator.common.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Journey : Parcelable {

    @Parcelize
    data class Rail(
        val origin: UiStation,
        val destination: UiStation
    ) : Journey()

    @Parcelize
    data class BusAndTram(val journeyCount: Int) : Journey()
}
