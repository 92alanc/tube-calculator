package com.alancamargo.tubecalculator.fares.domain.model

internal sealed class FareListResult {

    data class Success(val fareList: FareListRoot) : FareListResult()

    object NetworkError : FareListResult()

    object ServerError : FareListResult()

    object GenericError : FareListResult()
}
