package com.alancamargo.tubecalculator.search.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alancamargo.tubecalculator.search.data.model.db.DbStation

@Database(
    entities = [DbStation::class],
    version = 1,
    exportSchema = false
)
internal abstract class StationDatabase : RoomDatabase() {

    abstract fun getStationDao(): StationDao
}
