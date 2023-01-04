package com.alancamargo.tubecalculator.fares.di

import android.content.Context
import com.alancamargo.tubecalculator.core.database.DatabaseProvider
import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.fares.data.database.FaresDao
import com.alancamargo.tubecalculator.fares.data.database.FaresDatabase
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSource
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSourceImpl
import com.alancamargo.tubecalculator.fares.data.service.FaresService
import com.alancamargo.tubecalculator.fares.data.work.FaresCacheWorkScheduler
import com.alancamargo.tubecalculator.fares.data.work.FaresCacheWorkSchedulerImpl
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
    fun provideFaresService(apiProvider: ApiProvider): FaresService {
        return apiProvider.provideService(FaresService::class.java)
    }

    @Provides
    @Singleton
    fun provideFaresDao(databaseProvider: DatabaseProvider): FaresDao {
        val database = databaseProvider.provideDatabase(
            clazz = FaresDatabase::class,
            databaseName = "fares"
        )

        return database.getFaresDao()
    }

    @Provides
    @Singleton
    fun provideFaresCacheWorkScheduler(
        @ApplicationContext context: Context
    ): FaresCacheWorkScheduler = FaresCacheWorkSchedulerImpl(context)

    @Provides
    @Singleton
    fun provideFaresLocalDataSource(
        impl: FaresLocalDataSourceImpl
    ): FaresLocalDataSource = impl
}
