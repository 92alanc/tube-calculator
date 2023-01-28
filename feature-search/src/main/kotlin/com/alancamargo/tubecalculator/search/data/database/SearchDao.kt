package com.alancamargo.tubecalculator.search.data.database

import androidx.room.Dao
import androidx.room.Query
import com.alancamargo.tubecalculator.search.data.model.DbStation

@Dao
internal interface SearchDao {

    @Query("SELECT * FROM STATIONS")
    suspend fun getAllStations(): List<DbStation>
}
