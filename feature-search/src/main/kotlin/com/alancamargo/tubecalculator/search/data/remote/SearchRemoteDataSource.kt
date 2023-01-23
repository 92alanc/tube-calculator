package com.alancamargo.tubecalculator.search.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station

internal interface SearchRemoteDataSource {

    suspend fun searchStation(query: String): List<Station>
}
