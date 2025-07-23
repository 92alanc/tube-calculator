package com.alancamargo.tubecalculator.fares.ui.model

internal data class UiFareOption(
    val label: String,
    val origin: String,
    val destination: String,
    val description: String,
    val passengerType: String,
    val tickets: List<UiTicket>
)
