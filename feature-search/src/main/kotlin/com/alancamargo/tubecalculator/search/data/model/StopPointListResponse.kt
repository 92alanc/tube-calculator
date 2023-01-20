package com.alancamargo.tubecalculator.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StopPointListResponse(
    @SerialName("stopPoints") val stopPoints: List<StopPointResponse>
)
