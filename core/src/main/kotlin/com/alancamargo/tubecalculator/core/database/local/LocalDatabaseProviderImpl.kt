package com.alancamargo.tubecalculator.core.database.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import javax.inject.Inject
import kotlin.reflect.KClass

internal class LocalDatabaseProviderImpl @Inject constructor(
    private val context: Context
) : LocalDatabaseProvider {

    override fun <T : RoomDatabase> provideDatabase(clazz: KClass<T>, databaseName: String): T {
        return Room.databaseBuilder(
            context,
            clazz.java,
            databaseName
        ).fallbackToDestructiveMigration().build()
    }
}
