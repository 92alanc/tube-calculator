package com.alancamargo.tubecalculator.di

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object WorkManagerModule : Initializer<WorkManager> {

    @Provides
    @Singleton
    override fun create(@ApplicationContext context: Context): WorkManager {
        val config = Configuration.Builder().build()
        WorkManager.initialize(context, config)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
