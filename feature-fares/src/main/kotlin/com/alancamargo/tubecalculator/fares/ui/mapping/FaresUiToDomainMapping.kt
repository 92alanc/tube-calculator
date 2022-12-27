package com.alancamargo.tubecalculator.fares.ui.mapping

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal fun UiStation.toDomain() = Station(
    id = id,
    name = name,
    modes = modes.map { it.toDomain() },
    zones = zones
)

private fun UiMode.toDomain() = when (this) {
    UiMode.DLR -> Mode.DLR
    UiMode.ELIZABETH_LINE -> Mode.ELIZABETH_LINE
    UiMode.NATIONAL_RAIL -> Mode.NATIONAL_RAIL
    UiMode.OVERGROUND -> Mode.OVERGROUND
    UiMode.UNDERGROUND -> Mode.UNDERGROUND
}
