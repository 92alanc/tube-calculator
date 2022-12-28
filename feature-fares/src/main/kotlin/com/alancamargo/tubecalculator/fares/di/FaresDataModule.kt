package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.fares.data.service.FaresService
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
}
