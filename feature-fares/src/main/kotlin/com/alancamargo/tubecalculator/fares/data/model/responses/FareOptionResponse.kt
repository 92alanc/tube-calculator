package com.alancamargo.tubecalculator.fares.data.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FareOptionResponse(
    @SerialName("displayName") val label: String = "",
    @SerialName("from") val origin: String = "",
    @SerialName("to") val destination: String = "",
    @SerialName("routeDescription") val description: String = "",
    @SerialName("passengerType") val passengerType: String = "",
    @SerialName("ticketsAvailable") val tickets: List<TicketResponse> = emptyList()
)
