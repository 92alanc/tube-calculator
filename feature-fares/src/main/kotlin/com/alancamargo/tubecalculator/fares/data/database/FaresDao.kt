package com.alancamargo.tubecalculator.fares.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alancamargo.tubecalculator.fares.data.model.database.DbFareListRoot

@Dao
internal interface FaresDao {

    @Query("SELECT * FROM FARES WHERE originId = :originId AND destinationId = :destinationId")
    suspend fun getFares(originId: String, destinationId: String): DbFareListRoot?

    @Insert(entity = DbFareListRoot::class)
    suspend fun insertFares(fare: DbFareListRoot)

    @Update(entity = DbFareListRoot::class)
    suspend fun updateFares(fare: DbFareListRoot)

    @Query("SELECT * FROM FARES WHERE id = :id LIMIT 1")
    suspend fun getFare(id: String): DbFareListRoot?
}
