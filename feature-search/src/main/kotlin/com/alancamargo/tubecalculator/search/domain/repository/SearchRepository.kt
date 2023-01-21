package com.alancamargo.tubecalculator.search.domain.repository

import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.flow.Flow

internal interface SearchRepository {

    fun searchStation(query: String): Flow<StationListResult>

    fun populateDatabase(): Flow<Unit>
}
