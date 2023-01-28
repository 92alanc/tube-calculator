package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.domain.model.FareRoot

internal interface CalculateCheapestFareUseCase {

    operator fun invoke(
        railFares: List<FareRoot.RailFare>,
        busAndTramFare: FareRoot.BusAndTramFare?
    ): String
}
