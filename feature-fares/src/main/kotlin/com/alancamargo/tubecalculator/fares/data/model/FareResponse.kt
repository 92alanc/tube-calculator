package com.alancamargo.tubecalculator.fares.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FareResponse(
    @SerialName("passengerType") val passengerType: String,
    @SerialName("from") val origin: String,
    @SerialName("to") val destination: String,
    @SerialName("displayName") val name: String,
    @SerialName("routeDescription") val description: String,
    @SerialName("ticketsAvailable") val tickets: List<TicketResponse>,
    @SerialName("messages") val messages: List<FareMessageResponse>
)
