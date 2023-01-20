package com.alancamargo.tubecalculator.search.di

import com.alancamargo.tubecalculator.core.database.local.LocalDatabaseProvider
import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.search.data.api.SearchService
import com.alancamargo.tubecalculator.search.data.database.StationDao
import com.alancamargo.tubecalculator.search.data.database.StationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SearchDataModule {

    @Provides
    @Singleton
    fun provideSearchService(apiProvider: ApiProvider): SearchService {
        return apiProvider.provideService(SearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideStationDao(localDatabaseProvider: LocalDatabaseProvider): StationDao {
        val database = localDatabaseProvider.provideDatabaseFromAsset(
            clazz = StationDatabase::class,
            databaseName = "stations",
            assetPath = "stations.db"
        )

        return database.getStationDao()
    }
}
