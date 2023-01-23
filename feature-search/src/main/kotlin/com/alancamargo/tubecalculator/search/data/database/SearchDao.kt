package com.alancamargo.tubecalculator.search.data.database

import androidx.room.Dao
import androidx.room.Query
import com.alancamargo.tubecalculator.search.data.model.db.DbStation

@Dao
internal interface SearchDao {

    @Query("SELECT * FROM STATIONS WHERE NAME LIKE '%' || :query || '%'")
    suspend fun searchStation(query: String): List<DbStation>
}
