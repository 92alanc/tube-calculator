package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.flow.Flow

internal interface SearchStationUseCase {

    operator fun invoke(query: String): Flow<StationListResult>
}
