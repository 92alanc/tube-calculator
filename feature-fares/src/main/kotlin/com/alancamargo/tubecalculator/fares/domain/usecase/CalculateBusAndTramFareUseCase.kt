package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.domain.model.Fare

internal interface CalculateBusAndTramFareUseCase {

    operator fun invoke(busAndTramJourneyCount: Int): Fare.BusAndTramFare?
}
