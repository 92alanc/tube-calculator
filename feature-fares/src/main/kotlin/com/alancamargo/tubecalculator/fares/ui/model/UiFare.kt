package com.alancamargo.tubecalculator.fares.ui.model

internal sealed class UiFare {

    data class UiBusAndTramFare(val cost: String) : UiFare()

    data class UiRailFare(
        val header: String,
        val fareOptions: List<UiFareOption>,
        val messages: List<String>
    ) : UiFare()
}
