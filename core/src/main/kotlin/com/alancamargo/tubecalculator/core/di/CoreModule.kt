package com.alancamargo.tubecalculator.core.di

import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.core.network.ApiProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object CoreModule {

    @Provides
    fun provideApiProvider(impl: ApiProviderImpl): ApiProvider = impl
}
