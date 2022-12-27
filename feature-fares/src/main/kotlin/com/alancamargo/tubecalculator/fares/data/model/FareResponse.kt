package com.alancamargo.tubecalculator.fares.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FareResponse(
    @SerialName("displayName") val label: String,
    @SerialName("from") val origin: String,
    @SerialName("to") val destination: String,
    @SerialName("routeDescription") val description: String,
    @SerialName("passengerType") val passengerType: String,
    @SerialName("ticketsAvailable") val tickets: List<TicketResponse>,
    @SerialName("messages") val messages: List<FareMessageResponse>
)
