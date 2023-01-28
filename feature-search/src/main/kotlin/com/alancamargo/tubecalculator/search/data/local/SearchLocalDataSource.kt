package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.search.domain.model.StationListResult

internal interface SearchLocalDataSource {

    suspend fun getAllStations(): StationListResult
}
