package com.alancamargo.tubecalculator.fares.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.data.database.FaresDao
import com.alancamargo.tubecalculator.fares.data.model.database.DbFareListRoot
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class FaresLocalDataSourceImpl @Inject constructor(
    private val dao: FaresDao
) : FaresLocalDataSource {

    override fun getFares(origin: Station, destination: Station): Flow<FareListResult> = flow {
        val json = dao.getFares(origin.id, destination.id)?.jsonResponse
        json?.let {
            val fareList = Json.decodeFromString<List<FareListRoot>>(it)
            val result = FareListResult.Success(fareList)
            emit(result)
        } ?: run {
            throw Throwable("No results ")
        }
    }

    override fun saveFares(
        origin: Station,
        destination: Station,
        fares: List<FareListRoot>
    ): Flow<Unit> = flow {
        val id = "${origin.id}#${destination.id}"
        val dbFareListRoot = DbFareListRoot(
            id = id,
            originId = origin.id,
            destinationId = destination.id,
            jsonResponse = Json.encodeToString(fares)
        )

        val fare = dao.getFare(id)

        val task = if (fare != null) {
            dao.updateFares(dbFareListRoot)
        } else {
            dao.insertFares(dbFareListRoot)
        }

        emit(task)
    }
}
