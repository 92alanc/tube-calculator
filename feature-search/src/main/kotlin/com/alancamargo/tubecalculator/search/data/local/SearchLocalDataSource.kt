package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import kotlinx.coroutines.flow.Flow

internal interface SearchLocalDataSource {

    fun insertStation(station: Station): Flow<Unit>
}
