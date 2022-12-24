package com.alancamargo.tubecalculator.search.data.mapping

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.data.model.StationResponse

private const val REGEX_ZONE_SEPARATORS = "[+/]"

internal fun StationResponse.toDomain(): Station {
    val zones = zone?.split(REGEX_ZONE_SEPARATORS.toRegex())?.map {
        it.toInt()
    }.orEmpty()

    return Station(
        id = id,
        name = name,
        modes = modes.map { it.toDomain() },
        zones = zones
    )
}

private fun ModeResponse.toDomain() = when (this) {
    ModeResponse.DLR -> Mode.DLR
    ModeResponse.ELIZABETH_LINE -> Mode.ELIZABETH_LINE
    ModeResponse.NATIONAL_RAIL -> Mode.NATIONAL_RAIL
    ModeResponse.OVERGROUND -> Mode.OVERGROUND
    ModeResponse.UNDERGROUND -> Mode.UNDERGROUND
}
