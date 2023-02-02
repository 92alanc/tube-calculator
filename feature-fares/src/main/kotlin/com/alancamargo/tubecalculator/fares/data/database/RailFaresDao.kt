package com.alancamargo.tubecalculator.fares.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alancamargo.tubecalculator.fares.data.model.database.DbRailFare

@Dao
internal interface RailFaresDao {

    @Query("SELECT * FROM RAIL_FARES WHERE originId = :originId AND destinationId = :destinationId")
    suspend fun getRailFares(originId: String, destinationId: String): DbRailFare?

    @Insert(entity = DbRailFare::class)
    suspend fun insertRailFares(fare: DbRailFare)

    @Update(entity = DbRailFare::class)
    suspend fun updateRailFares(fare: DbRailFare)

    @Query("SELECT COUNT() FROM RAIL_FARES WHERE id = :id")
    suspend fun getRailFareCount(id: String): Int

    @Query("DELETE FROM RAIL_FARES")
    suspend fun deleteAllRailFares()
}
