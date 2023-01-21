package com.alancamargo.tubecalculator.search.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alancamargo.tubecalculator.search.data.model.db.DbStation

@Dao
internal interface SearchDao {

    @Insert(entity = DbStation::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStation(station: DbStation)

    @Query("SELECT * FROM STATIONS WHERE NAME LIKE :query")
    suspend fun searchStation(query: String): List<DbStation>
}
