package com.alancamargo.tubecalculator.common.ui.mapping

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation

fun Station.toUi() = UiStation(
    id = id,
    name = name,
    modes = modes.map { it.toUi() }
)

private fun Mode.toUi() = when (this) {
    Mode.DLR -> UiMode.DLR
    Mode.ELIZABETH_LINE -> UiMode.ELIZABETH_LINE
    Mode.NATIONAL_RAIL -> UiMode.NATIONAL_RAIL
    Mode.OVERGROUND -> UiMode.OVERGROUND
    Mode.UNDERGROUND -> UiMode.UNDERGROUND
}
