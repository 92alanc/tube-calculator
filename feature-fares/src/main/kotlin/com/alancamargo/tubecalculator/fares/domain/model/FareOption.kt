package com.alancamargo.tubecalculator.fares.domain.model

internal data class FareOption(
    val label: String,
    val origin: String,
    val destination: String,
    val description: String,
    val passengerType: String,
    val tickets: List<Ticket>
)
