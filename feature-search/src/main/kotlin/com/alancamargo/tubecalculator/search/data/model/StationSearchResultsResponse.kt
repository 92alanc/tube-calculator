package com.alancamargo.tubecalculator.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StationSearchResultsResponse(
    @SerialName("matches") val matches: List<StationResponse>?
)