package com.alancamargo.tubecalculator.fares.data.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TicketTimeResponse(
    @SerialName("type") val label: String = "",
    @SerialName("description") val description: String = ""
)
