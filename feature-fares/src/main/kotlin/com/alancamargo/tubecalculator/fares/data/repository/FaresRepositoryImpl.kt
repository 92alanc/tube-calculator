package com.alancamargo.tubecalculator.fares.data.repository

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

private const val KEY_BUS_AND_TRAM_BASE_FARE = "bus_and_tram_base_fare"

internal class FaresRepositoryImpl @Inject constructor(
    private val remoteDataSource: FaresRemoteDataSource,
    private val remoteConfigManager: RemoteConfigManager
) : FaresRepository {

    override fun getFares(origin: Station, destination: Station): Flow<FareListResult> {
        return remoteDataSource.getFares(origin, destination)
    }

    override fun getBusAndTramBaseFare(): BigDecimal {
        val baseFare = remoteConfigManager.getDouble(KEY_BUS_AND_TRAM_BASE_FARE)
        return BigDecimal.valueOf(baseFare)
    }
}
