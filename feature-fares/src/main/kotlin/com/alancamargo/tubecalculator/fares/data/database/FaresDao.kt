package com.alancamargo.tubecalculator.fares.data.database

import androidx.room.Dao
import androidx.room.Query
import com.alancamargo.tubecalculator.fares.data.model.database.DbFareListRoot

@Dao
internal interface FaresDao {

    @Query(
        "SELECT * FROM FARES WHERE queryId = (SELECT ID FROM QUERIES WHERE originId = :originId AND destinationId = :destinationId)"
    )
    suspend fun getFares(originId: String, destinationId: String): DbFareListRoot?
}
