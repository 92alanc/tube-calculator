package com.alancamargo.tubecalculator.core.database

import androidx.room.RoomDatabase
import kotlin.reflect.KClass

interface DatabaseProvider {

    fun <T : RoomDatabase> provideDatabase(clazz: KClass<T>, databaseName: String): T
}
