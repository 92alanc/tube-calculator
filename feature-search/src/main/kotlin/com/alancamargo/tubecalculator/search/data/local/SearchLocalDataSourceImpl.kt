package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.database.SearchDao
import com.alancamargo.tubecalculator.search.data.mapping.toDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchLocalDataSourceImpl @Inject constructor(
    private val dao: SearchDao
) : SearchLocalDataSource {

    override fun insertStation(station: Station): Flow<Unit> = flow {
        val dbStation = station.toDb()
        dao.insertStation(dbStation)
    }
}
