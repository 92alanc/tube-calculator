package com.alancamargo.tubecalculator.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StopPointResponse(
    @SerialName("stationNaptan") val id: String? = null,
    @SerialName("commonName") val name: String,
    @SerialName("modes") val modes: List<ModeResponse>
)
