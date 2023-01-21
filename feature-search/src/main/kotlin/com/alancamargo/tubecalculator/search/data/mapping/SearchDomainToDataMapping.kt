package com.alancamargo.tubecalculator.search.data.mapping

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.data.model.db.DbStation
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun Station.toDb(): DbStation {
    val modeResponses = modes.map { it.toResponse() }
    val modesJson = Json.encodeToString(modeResponses)

    return DbStation(
        id = id,
        name = name,
        modesJson = modesJson
    )
}

private fun Mode.toResponse(): ModeResponse = when (this) {
    Mode.DLR -> ModeResponse.DLR
    Mode.ELIZABETH_LINE -> ModeResponse.ELIZABETH_LINE
    Mode.NATIONAL_RAIL -> ModeResponse.NATIONAL_RAIL
    Mode.OVERGROUND -> ModeResponse.OVERGROUND
    Mode.UNDERGROUND -> ModeResponse.UNDERGROUND
}
