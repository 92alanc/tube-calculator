package com.alancamargo.tubecalculator.fares.di

import androidx.work.WorkManager
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
        workManager: WorkManager
    ): FaresCacheWorkScheduler = FaresCacheWorkSchedulerImpl(workManager)

    @Provides
    @Singleton
    fun provideFaresLocalDataSource(
        impl: FaresLocalDataSourceImpl
    ): FaresLocalDataSource = impl
}
