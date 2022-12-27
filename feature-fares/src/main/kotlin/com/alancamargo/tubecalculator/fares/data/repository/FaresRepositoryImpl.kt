package com.alancamargo.tubecalculator.fares.data.repository

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class FaresRepositoryImpl @Inject constructor(
    private val remoteDataSource: FaresRemoteDataSource
) : FaresRepository {

    override fun getFares(origin: Station, destination: Station): Flow<FareListResult> {
        return remoteDataSource.getFares(origin, destination)
    }
}
