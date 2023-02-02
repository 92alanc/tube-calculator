package com.alancamargo.tubecalculator.fares.domain.model

internal sealed class Fare {

    data class BusAndTramFare(val cost: String) : Fare()

    data class RailFare(
        val header: String,
        val fareOptions: List<FareOption>,
        val messages: List<String>
    ) : Fare()
}
