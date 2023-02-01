package com.alancamargo.tubecalculator.fares.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alancamargo.tubecalculator.fares.data.model.database.DbRailFare

@Database(
    entities = [DbRailFare::class],
    version = 3,
    exportSchema = false
)
internal abstract class RailFaresDatabase : RoomDatabase() {

    abstract fun getRailFaresDao(): RailFaresDao
}
