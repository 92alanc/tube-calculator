package com.alancamargo.tubecalculator.fares.domain.repository

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

internal interface FaresRepository {

    fun getFares(origin: Station, destination: Station): Flow<FareListResult>

    fun getBusAndTramBaseFare(): BigDecimal
}
