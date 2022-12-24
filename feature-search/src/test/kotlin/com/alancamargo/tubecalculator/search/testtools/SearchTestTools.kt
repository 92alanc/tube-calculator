package com.alancamargo.tubecalculator.search.testtools

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.data.model.StationResponse
import com.alancamargo.tubecalculator.search.data.model.StationSearchResultsResponse

internal const val SEARCH_QUERY = "camden"

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
