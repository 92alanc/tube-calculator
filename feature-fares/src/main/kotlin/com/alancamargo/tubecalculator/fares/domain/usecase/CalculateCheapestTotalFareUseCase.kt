package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.domain.model.Fare

internal interface CalculateCheapestTotalFareUseCase {

    operator fun invoke(fares: List<Fare>): String
}
