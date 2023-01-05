package com.alancamargo.tubecalculator.fares.data.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FareListRootResponse(
    @SerialName("header") val header: String = "",
    @SerialName("rows") val fares: List<FareResponse> = emptyList(),
    @SerialName("messages") val messages: List<FareMessageResponse> = emptyList()
)
