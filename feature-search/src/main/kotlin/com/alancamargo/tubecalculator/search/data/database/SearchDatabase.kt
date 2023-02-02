package com.alancamargo.tubecalculator.search.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alancamargo.tubecalculator.search.data.model.DbStation

@Database(
    entities = [DbStation::class],
    version = 1,
    exportSchema = false
)
internal abstract class SearchDatabase : RoomDatabase() {

    abstract fun getStationDao(): SearchDao
}
