package com.alancamargo.tubecalculator.fares.data.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TicketResponse(
    @SerialName("ticketType") val typeWrapper: TicketTypeResponseWrapper,
    @SerialName("ticketTime") val time: TicketTimeResponse = TicketTimeResponse(),
    @SerialName("cost") val cost: String = ""
)
