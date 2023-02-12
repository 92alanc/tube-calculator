package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.fares.data.service.RailFaresService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FaresNetworkModule {

    @Provides
    @Singleton
    fun provideRailFaresService(apiProvider: ApiProvider): RailFaresService {
        return apiProvider.provideService(RailFaresService::class.java)
    }
}
