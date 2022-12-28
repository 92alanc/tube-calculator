package com.alancamargo.tubecalculator.fares.domain.model

internal sealed class FareListResult {

    data class Success(val fareList: List<FareListRoot>) : FareListResult()

    object Empty : FareListResult()

    object NetworkError : FareListResult()

    object ServerError : FareListResult()

    object GenericError : FareListResult()
}
