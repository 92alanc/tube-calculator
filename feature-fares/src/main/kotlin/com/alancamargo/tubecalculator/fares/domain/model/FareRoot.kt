package com.alancamargo.tubecalculator.fares.domain.model

internal sealed class FareRoot {

    data class BusAndTramFare(val fare: String) : FareRoot()

    data class RailFare(
        val header: String,
        val fares: List<Fare>,
        val messages: List<String>
    ) : FareRoot()
}
