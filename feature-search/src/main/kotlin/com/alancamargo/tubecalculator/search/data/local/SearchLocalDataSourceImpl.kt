package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.search.data.database.SearchDao
import com.alancamargo.tubecalculator.search.data.mapping.toDomain
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class SearchLocalDataSourceImpl @Inject constructor(
    private val dao: SearchDao
) : SearchLocalDataSource {

    override suspend fun searchStation(query: String): StationListResult {
        return try {
            val dbStations = dao.searchStation(query)
            val stations = dbStations.map { it.toDomain() }

            delay(100)
            StationListResult.Success(stations)
        } catch (t: Throwable) {
            StationListResult.GenericError
        }
    }
}
