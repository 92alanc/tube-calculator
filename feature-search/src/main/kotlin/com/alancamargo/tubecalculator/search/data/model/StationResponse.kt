package com.alancamargo.tubecalculator.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StationResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("modes") val modes: List<ModeResponse>,
    @SerialName("zone") val zone: String?
)
