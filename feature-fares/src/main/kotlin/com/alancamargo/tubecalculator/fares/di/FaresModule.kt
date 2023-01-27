package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalytics
import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalyticsImpl
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSourceImpl
import com.alancamargo.tubecalculator.fares.data.repository.FaresRepositoryImpl
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateBusAndTramFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateBusAndTramFareUseCaseImpl
import com.alancamargo.tubecalculator.fares.domain.usecase.GetRailFaresUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.GetRailFaresUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class FaresModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFaresRemoteDataSource(impl: FaresRemoteDataSourceImpl): FaresRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindFaresRepository(impl: FaresRepositoryImpl): FaresRepository

    @Binds
    @ViewModelScoped
    abstract fun bindGetRailFaresUseCase(impl: GetRailFaresUseCaseImpl): GetRailFaresUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindCalculateBusAndTramFareUseCase(
        impl: CalculateBusAndTramFareUseCaseImpl
    ): CalculateBusAndTramFareUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFaresAnalytics(impl: FaresAnalyticsImpl): FaresAnalytics
}
