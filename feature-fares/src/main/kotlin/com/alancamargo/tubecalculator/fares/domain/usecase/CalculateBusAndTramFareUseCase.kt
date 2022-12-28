package com.alancamargo.tubecalculator.fares.domain.usecase

internal interface CalculateBusAndTramFareUseCase {

    operator fun invoke(busAndTramJourneyCount: Int): String
}
