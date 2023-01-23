package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.database.SearchDao
import com.alancamargo.tubecalculator.search.data.mapping.toDomain
import javax.inject.Inject

internal class SearchLocalDataSourceImpl @Inject constructor(
    private val dao: SearchDao
) : SearchLocalDataSource {

    override suspend fun searchStation(query: String): List<Station> {
        val dbStations = dao.searchStation(query)
        return dbStations.map { it.toDomain() }
    }
}
