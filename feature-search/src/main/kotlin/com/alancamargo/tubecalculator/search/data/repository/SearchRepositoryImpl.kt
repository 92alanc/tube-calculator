package com.alancamargo.tubecalculator.search.data.repository

import com.alancamargo.tubecalculator.search.data.local.SearchLocalDataSource
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val localDataSource: SearchLocalDataSource
) : SearchRepository {

    override fun getAllStations(): Flow<StationListResult> = flow {
        val stations = localDataSource.getAllStations()
        emit(stations)
    }
}
