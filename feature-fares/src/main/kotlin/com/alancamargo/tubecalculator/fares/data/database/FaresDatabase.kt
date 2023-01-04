package com.alancamargo.tubecalculator.fares.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alancamargo.tubecalculator.fares.data.model.database.DbFareListRoot

@Database(
    entities = [DbFareListRoot::class],
    version = 2,
    exportSchema = false
)
internal abstract class FaresDatabase : RoomDatabase() {

    abstract fun getFaresDao(): FaresDao
}
