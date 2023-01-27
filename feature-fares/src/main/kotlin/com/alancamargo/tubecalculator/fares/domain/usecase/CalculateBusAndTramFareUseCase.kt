package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.domain.model.FareRoot

internal interface CalculateBusAndTramFareUseCase {

    operator fun invoke(busAndTramJourneyCount: Int): FareRoot.BusAndTramFare?
}
