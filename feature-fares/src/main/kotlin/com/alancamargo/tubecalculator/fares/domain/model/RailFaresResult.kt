package com.alancamargo.tubecalculator.fares.domain.model

internal sealed class RailFaresResult {

    data class Success(val railFares: List<Fare.RailFare>) : RailFaresResult()

    object InvalidQueryError : RailFaresResult()

    object NetworkError : RailFaresResult()

    object GenericError : RailFaresResult()
}
