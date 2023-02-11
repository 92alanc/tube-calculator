package com.alancamargo.tubecalculator.fares.testtools

import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal fun stubUiStation() = UiStation(
    id = "12345",
    name = "Marble Arch",
    modes = listOf(UiMode.UNDERGROUND)
)
