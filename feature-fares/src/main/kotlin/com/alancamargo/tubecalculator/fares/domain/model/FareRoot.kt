package com.alancamargo.tubecalculator.fares.domain.model

internal sealed class FareRoot {

    data class BusAndTramFare(val fare: String) : FareRoot()

    data class RailFare(
        val header: String,
        val fareOptions: List<FareOption>,
        val messages: List<String>
    ) : FareRoot()
}
