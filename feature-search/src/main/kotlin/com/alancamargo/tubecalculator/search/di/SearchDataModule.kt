package com.alancamargo.tubecalculator.search.di

import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.search.data.api.SearchService
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
}
