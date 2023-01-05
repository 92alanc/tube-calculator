package com.alancamargo.tubecalculator.fares.data.model.database

import com.alancamargo.tubecalculator.fares.data.model.responses.FareListRootResponse
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteDatabaseFareListWrapper(
    val fareList: List<FareListRootResponse> = emptyList()
)
