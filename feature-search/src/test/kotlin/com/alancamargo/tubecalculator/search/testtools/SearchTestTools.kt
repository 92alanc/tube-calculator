package com.alancamargo.tubecalculator.search.testtools

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.common.ui.mapping.toUi
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.data.model.StationResponse
import com.alancamargo.tubecalculator.search.data.model.StationSearchResultsResponse
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.flow.flow

internal const val SEARCH_QUERY = "camden"

internal fun stubSuccessfulSearchFlow() = flow<StationListResult> {
    val stations = stubStationList()
    val result = StationListResult.Success(stations)
    emit(result)
}

internal fun stubUiStationList() = stubStationList().map { it.toUi() }

internal fun stubSearchResultsResponse() = StationSearchResultsResponse(
    matches = listOf(
        StationResponse(
            id = "12345",
            name = "Camden Road",
            modes = listOf(ModeResponse.OVERGROUND),
            zone = "2"
        ),
        StationResponse(
            id = "67890",
            name = "Camden Town",
            modes = listOf(ModeResponse.UNDERGROUND),
            zone = "2"
        )
    )
)

internal fun stubStationList() = listOf(
    Station(
        id = "12345",
        name = "Camden Road",
        modes = listOf(Mode.OVERGROUND),
        zones = listOf(2)
    ),
    Station(
        id = "67890",
        name = "Camden Town",
        modes = listOf(Mode.UNDERGROUND),
        zones = listOf(2)
    )
)

internal fun stubUiStation(name: String) = UiStation(
    id = "123",
    name = name,
    modes = listOf(UiMode.ELIZABETH_LINE, UiMode.NATIONAL_RAIL, UiMode.OVERGROUND),
    zones = listOf(6)
)
