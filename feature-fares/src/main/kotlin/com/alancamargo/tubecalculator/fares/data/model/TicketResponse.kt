package com.alancamargo.tubecalculator.fares.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TicketResponse(
    @SerialName("ticketType") val type: TicketTypeResponse,
    @SerialName("ticketTime") val time: TicketTimeResponse,
    @SerialName("cost") val cost: String
)
