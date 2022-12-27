package com.alancamargo.tubecalculator.search.data.mapping

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.data.model.StationResponse

internal fun StationResponse.toDomain() = Station(
    id = id,
    name = name,
    modes = modes.filterNot { it == ModeResponse.BUS }.map { it.toDomain() }
)

private fun ModeResponse.toDomain() = when (this) {
    ModeResponse.DLR -> Mode.DLR
    ModeResponse.ELIZABETH_LINE -> Mode.ELIZABETH_LINE
    ModeResponse.NATIONAL_RAIL -> Mode.NATIONAL_RAIL
    ModeResponse.OVERGROUND -> Mode.OVERGROUND
    ModeResponse.UNDERGROUND -> Mode.UNDERGROUND
    else -> throw IllegalArgumentException("Invalid mode")
}
