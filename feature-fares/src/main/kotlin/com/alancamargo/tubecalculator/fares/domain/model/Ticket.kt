package com.alancamargo.tubecalculator.fares.domain.model

internal data class Ticket(
    val type: TicketType,
    val time: TicketTime,
    val cost: String
)
