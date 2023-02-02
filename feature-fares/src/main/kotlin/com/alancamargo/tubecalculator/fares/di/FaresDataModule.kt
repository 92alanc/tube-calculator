package com.alancamargo.tubecalculator.fares.di

import android.content.Context
import com.alancamargo.tubecalculator.core.database.local.LocalDatabaseProvider
import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.fares.data.database.RailFaresDao
import com.alancamargo.tubecalculator.fares.data.database.RailFaresDatabase
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSource
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSourceImpl
import com.alancamargo.tubecalculator.fares.data.service.RailFaresService
import com.alancamargo.tubecalculator.fares.data.work.RailFaresCacheWorkScheduler
import com.alancamargo.tubecalculator.fares.data.work.RailFaresCacheWorkSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FaresDataModule {

    @Provides
    @Singleton
    fun provideRailFaresService(apiProvider: ApiProvider): RailFaresService {
        return apiProvider.provideService(RailFaresService::class.java)
    }

    @Provides
    @Singleton
    fun provideRailFaresDao(localDatabaseProvider: LocalDatabaseProvider): RailFaresDao {
        val database = localDatabaseProvider.provideDatabase(
            clazz = RailFaresDatabase::class,
            databaseName = "fares"
        )

        return database.getRailFaresDao()
    }

    @Provides
    @Singleton
    fun provideRailFaresCacheWorkScheduler(
        @ApplicationContext context: Context
    ): RailFaresCacheWorkScheduler = RailFaresCacheWorkSchedulerImpl(context)

    @Provides
    @Singleton
    fun provideFaresLocalDataSource(
        impl: FaresLocalDataSourceImpl
    ): FaresLocalDataSource = impl
}
