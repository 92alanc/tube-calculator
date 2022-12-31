package com.alancamargo.tubecalculator.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import javax.inject.Inject
import kotlin.reflect.KClass

internal class DatabaseProviderImpl @Inject constructor(
    private val context: Context
) : DatabaseProvider {

    override fun <T : RoomDatabase> provideDatabase(clazz: KClass<T>, databaseName: String): T {
        return Room.databaseBuilder(
            context,
            clazz.java,
            databaseName
        ).fallbackToDestructiveMigration().build()
    }
}
