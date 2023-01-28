package com.alancamargo.tubecalculator.search.domain.repository

import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.flow.Flow

internal interface SearchRepository {

    fun getAllStations(): Flow<StationListResult>
}
