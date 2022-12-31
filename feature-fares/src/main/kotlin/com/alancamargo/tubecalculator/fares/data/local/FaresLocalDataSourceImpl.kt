package com.alancamargo.tubecalculator.fares.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.data.database.FaresDao
import com.alancamargo.tubecalculator.fares.data.mapping.toData
import com.alancamargo.tubecalculator.fares.data.mapping.toDomain
import com.alancamargo.tubecalculator.fares.data.model.database.DbFareListRoot
import com.alancamargo.tubecalculator.fares.data.model.responses.FareListRootResponse
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class FaresLocalDataSourceImpl @Inject constructor(
    private val dao: FaresDao
) : FaresLocalDataSource {

    override suspend fun getFares(origin: Station, destination: Station): FareListResult {
        val json = dao.getFares(origin.id, destination.id)?.jsonResponse
        json?.let {
            val fareList = Json.decodeFromString<List<FareListRootResponse>>(it).map { response ->
                response.toDomain()
            }
            return FareListResult.Success(fareList)
        } ?: run {
            throw Throwable("No results")
        }
    }

    override suspend fun saveFares(
        origin: Station,
        destination: Station,
        fares: List<FareListRoot>
    ) {
        val id = "${origin.id}#${destination.id}"
        val dbFareListRoot = DbFareListRoot(
            id = id,
            originId = origin.id,
            destinationId = destination.id,
            jsonResponse = Json.encodeToString(fares.map { it.toData() })
        )

        val fare = dao.getFare(id)

        if (fare != null) {
            dao.updateFares(dbFareListRoot)
        } else {
            dao.insertFares(dbFareListRoot)
        }
    }
}
