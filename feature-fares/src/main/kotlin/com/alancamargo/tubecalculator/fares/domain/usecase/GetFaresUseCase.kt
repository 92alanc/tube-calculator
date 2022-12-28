package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import kotlinx.coroutines.flow.Flow

internal interface GetFaresUseCase {

    operator fun invoke(origin: Station, destination: Station): Flow<FareListResult>
}
