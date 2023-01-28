package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.search.data.database.SearchDao
import com.alancamargo.tubecalculator.search.data.mapping.toDomain
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import javax.inject.Inject

internal class SearchLocalDataSourceImpl @Inject constructor(
    private val dao: SearchDao
) : SearchLocalDataSource {

    override suspend fun getAllStations(): StationListResult {
        return try {
            val dbStations = dao.getAllStations()
            val stations = dbStations.map { it.toDomain() }
            StationListResult.Success(stations)
        } catch (t: Throwable) {
            StationListResult.GenericError
        }
    }
}
