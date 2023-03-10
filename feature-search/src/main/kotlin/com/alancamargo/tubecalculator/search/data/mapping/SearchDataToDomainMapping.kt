package com.alancamargo.tubecalculator.search.data.mapping

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.model.DbStation
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal fun DbStation.toDomain(): Station {
    val modeResponseList = Json.decodeFromString<List<ModeResponse>>(modesJson)
    val modes = modeResponseList.map { it.toDomain() }

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
    else -> throw IllegalArgumentException("Invalid mode")
}
