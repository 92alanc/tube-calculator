package com.alancamargo.tubecalculator.fares.data.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RailFareResponse(
    @SerialName("header") val header: String = "",
    @SerialName("rows") val fareOptions: List<FareOptionResponse> = emptyList(),
    @SerialName("messages") val messages: List<FareMessageResponse> = emptyList()
)
