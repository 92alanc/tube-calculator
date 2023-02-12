package com.alancamargo.tubecalculator.search.ui.testtools

import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal const val STATION_1 = "Farringdon"
internal const val STATION_2 = "Marylebone"
internal const val BUS_AND_TRAM_JOURNEY_COUNT = 2

internal fun stubUiStation(name: String) = UiStation(
    id = "12345",
    name = name,
    modes = listOf(UiMode.UNDERGROUND)
)
