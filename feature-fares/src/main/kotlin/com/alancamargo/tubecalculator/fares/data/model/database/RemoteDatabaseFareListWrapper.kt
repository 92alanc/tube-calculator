package com.alancamargo.tubecalculator.fares.data.model.database

import com.alancamargo.tubecalculator.fares.data.model.responses.RailFareResponse
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteDatabaseFareListWrapper(
    val fareList: List<RailFareResponse> = emptyList()
)
