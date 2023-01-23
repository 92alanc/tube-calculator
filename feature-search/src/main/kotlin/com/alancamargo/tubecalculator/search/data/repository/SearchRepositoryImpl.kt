package com.alancamargo.tubecalculator.search.data.repository

import com.alancamargo.tubecalculator.search.data.local.SearchLocalDataSource
import com.alancamargo.tubecalculator.search.data.remote.SearchRemoteDataSource
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val localDataSource: SearchLocalDataSource
) : SearchRepository {

    override fun searchStation(query: String): Flow<StationListResult> = flow {
        val localResult = localDataSource.searchStation(query)

        if (localResult is StationListResult.Success) {
            emit(localResult)
        } else {
            val remoteResult = remoteDataSource.searchStation(query)
            emit(remoteResult)
        }
    }
}
