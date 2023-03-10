package com.alancamargo.tubecalculator.fares.data.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class TicketTypeResponse {

    @SerialName("Pay as you go")
    PAY_AS_YOU_GO,

    @SerialName("CashSingle")
    CASH
}
