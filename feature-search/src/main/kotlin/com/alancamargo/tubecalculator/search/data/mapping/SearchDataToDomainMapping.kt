package com.alancamargo.tubecalculator.search.data.mapping

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.model.DbStation
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import kotlinx.serialization.json.Json

internal fun DbStation.toDomain(): Station {
    val modes = Json.decodeFromString<List<String>>(modesJson)
        .map(ModeResponse::fromString)
        .map { it.toDomain() }

    return Station(
        id = id,
        name = name,
        modes = modes
    )
}

private fun ModeResponse.toDomain() = when (this) {
    ModeResponse.DLR -> Mode.DLR
    ModeResponse.ELIZABETH_LINE -> Mode.ELIZABETH_LINE
    ModeResponse.NATIONAL_RAIL -> Mode.NATIONAL_RAIL
    ModeResponse.OVERGROUND -> Mode.OVERGROUND
    ModeResponse.UNDERGROUND -> Mode.UNDERGROUND
}
