package com.alancamargo.tubecalculator.search.domain.model

import com.alancamargo.tubecalculator.common.domain.model.Station

internal sealed class StationListResult {

    data class Success(val stations: List<Station>) : StationListResult()

    object NetworkError : StationListResult()

    object ServerError : StationListResult()

    object GenericError : StationListResult()
}
