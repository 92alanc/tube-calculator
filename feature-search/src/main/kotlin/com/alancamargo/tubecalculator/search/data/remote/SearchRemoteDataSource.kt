package com.alancamargo.tubecalculator.search.data.remote

import com.alancamargo.tubecalculator.search.domain.model.StationListResult

internal interface SearchRemoteDataSource {

    suspend fun searchStation(query: String): StationListResult
}
