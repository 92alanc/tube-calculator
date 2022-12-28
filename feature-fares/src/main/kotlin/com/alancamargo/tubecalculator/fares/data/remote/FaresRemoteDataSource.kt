package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import kotlinx.coroutines.flow.Flow

internal interface FaresRemoteDataSource {

    fun getFares(origin: Station, destination: Station): Flow<FareListResult>
}
