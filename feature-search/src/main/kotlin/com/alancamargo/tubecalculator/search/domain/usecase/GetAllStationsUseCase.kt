package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.flow.Flow

internal interface GetAllStationsUseCase {

    operator fun invoke(): Flow<StationListResult>
}
