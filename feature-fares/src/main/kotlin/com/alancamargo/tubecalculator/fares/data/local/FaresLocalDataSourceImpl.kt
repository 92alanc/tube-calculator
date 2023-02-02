package com.alancamargo.tubecalculator.fares.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.data.database.RailFaresDao
import com.alancamargo.tubecalculator.fares.data.mapping.toData
import com.alancamargo.tubecalculator.fares.data.mapping.toDomain
import com.alancamargo.tubecalculator.fares.data.model.database.DbRailFare
import com.alancamargo.tubecalculator.fares.data.model.responses.RailFareResponse
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class FaresLocalDataSourceImpl @Inject constructor(
    private val dao: RailFaresDao
) : FaresLocalDataSource {

    override suspend fun getRailFares(origin: Station, destination: Station): RailFaresResult {
        val json = dao.getRailFares(origin.id, destination.id)?.jsonResponse
        json?.let {
            val railFares = Json.decodeFromString<List<RailFareResponse>>(it).map { response ->
                response.toDomain()
            }
            return RailFaresResult.Success(railFares)
        } ?: run {
            throw Throwable("No results")
        }
    }

    override suspend fun saveRailFares(
        origin: Station,
        destination: Station,
        railFares: List<Fare.RailFare>
    ) {
        val id = "${origin.id}#${destination.id}"
        val dbRailFares = DbRailFare(
            id = id,
            originId = origin.id,
            destinationId = destination.id,
            jsonResponse = Json.encodeToString(railFares.map { it.toData() })
        )

        val count = dao.getRailFareCount(id)

        if (count > 0) {
            dao.updateRailFares(dbRailFares)
        } else {
            dao.insertRailFares(dbRailFares)
        }
    }

    override suspend fun clearCache() {
        dao.deleteAllRailFares()
    }
}
