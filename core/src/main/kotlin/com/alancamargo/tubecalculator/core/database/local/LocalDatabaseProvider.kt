package com.alancamargo.tubecalculator.core.database.local

import androidx.room.RoomDatabase
import kotlin.reflect.KClass

interface LocalDatabaseProvider {

    fun <T : RoomDatabase> provideDatabase(clazz: KClass<T>, databaseName: String): T
}
