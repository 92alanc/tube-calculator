package com.alancamargo.tubecalculator.search.data.remote

import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.flow.Flow

internal interface SearchRemoteDataSource {

    fun searchStation(query: String): Flow<StationListResult>
}
