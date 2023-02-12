package com.alancamargo.tubecalculator.search.testtools

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.common.ui.mapping.toUi
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.search.data.model.DbStation
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal const val MIN_QUERY_LENGTH = 3
internal const val SEARCH_QUERY = "camden"

internal fun stubSuccessfulStationListResultFlow() = flow<StationListResult> {
    val stations = stubStationList()
    val result = StationListResult.Success(stations)
    emit(result)
}

internal fun stubUiStationList() = stubStationList().map { it.toUi() }

internal fun stubDbStationList() = stubStationList().map { it.toDb() }

internal fun stubStationList() = listOf(
    Station(
        id = "12345",
        name = "Camden Road",
        modes = listOf(Mode.OVERGROUND)
    ),
    Station(
        id = "67890",
        name = "Camden Town",
        modes = listOf(Mode.UNDERGROUND)
    )
)

internal fun stubRailJourney() = Journey.Rail(
    origin = stubUiStation(),
    destination = stubUiStation()
)

internal fun stubBusAndTramJourney() = Journey.BusAndTram(journeyCount = 2)

internal fun stubUiStation() = UiStation(
    id = "123",
    name = "Maida Vale Underground Station",
    modes = listOf(UiMode.OVERGROUND)
)

private fun Station.toDb(): DbStation {
    val modeResponseList = modes.map { it.toResponse() }
    val modesJson = Json.encodeToString(modeResponseList)

    return DbStation(
        id = id,
        name = name,
        modesJson = modesJson
    )
}

private fun Mode.toResponse() = when (this) {
    Mode.DLR -> ModeResponse.DLR
    Mode.ELIZABETH_LINE -> ModeResponse.ELIZABETH_LINE
    Mode.NATIONAL_RAIL -> ModeResponse.NATIONAL_RAIL
    Mode.OVERGROUND -> ModeResponse.OVERGROUND
    Mode.UNDERGROUND -> ModeResponse.UNDERGROUND
}
