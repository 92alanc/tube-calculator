package com.alancamargo.tubecalculator.fares.ui.model

internal data class UiTicket(
    val type: UiTicketType,
    val time: UiTicketTime,
    val cost: String
)
