package com.alancamargo.tubecalculator.home.testtools

import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal fun stubRailJourney() = Journey.Rail(
    origin = UiStation(id = "12345", name = "Holborn", modes = listOf(UiMode.UNDERGROUND)),
    destination = UiStation(id = "54321", name = "Uxbridge", modes = listOf(UiMode.UNDERGROUND))
)

internal fun stubBusAndTramJourney() = Journey.BusAndTram(journeyCount = 1)
