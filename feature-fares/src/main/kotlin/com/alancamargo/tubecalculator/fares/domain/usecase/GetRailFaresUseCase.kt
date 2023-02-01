package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import kotlinx.coroutines.flow.Flow

internal interface GetRailFaresUseCase {

    operator fun invoke(origin: Station, destination: Station): Flow<RailFaresResult>
}
