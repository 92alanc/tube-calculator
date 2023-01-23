package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station

internal interface SearchLocalDataSource {

    suspend fun searchStation(query: String): List<Station>
}
