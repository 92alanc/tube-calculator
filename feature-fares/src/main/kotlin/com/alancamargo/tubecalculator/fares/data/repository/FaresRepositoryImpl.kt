package com.alancamargo.tubecalculator.fares.data.repository

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSource
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import javax.inject.Inject

private const val KEY_BUS_AND_TRAM_BASE_FARE = "bus_and_tram_base_fare"

internal class FaresRepositoryImpl @Inject constructor(
    private val remoteDataSource: FaresRemoteDataSource,
    private val remoteConfigManager: RemoteConfigManager,
    private val localDataSource: FaresLocalDataSource
) : FaresRepository {

    override fun getRailFares(origin: Station, destination: Station): Flow<RailFaresResult> = flow {
        val result = try {
            localDataSource.getRailFares(origin, destination)
        } catch (t: Throwable) {
            remoteDataSource.getRailFares(origin, destination).apply {
                if (this is RailFaresResult.Success) {
                    localDataSource.saveRailFares(origin, destination, railFares)
                }
            }
        }

        emit(result)
    }

    override fun getBusAndTramBaseFare(): BigDecimal {
        val baseFare = remoteConfigManager.getDouble(KEY_BUS_AND_TRAM_BASE_FARE)
        return BigDecimal.valueOf(baseFare)
    }
}
