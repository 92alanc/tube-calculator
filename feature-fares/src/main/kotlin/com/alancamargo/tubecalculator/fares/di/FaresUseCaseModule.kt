package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.fares.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class FaresUseCaseModule {

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
    abstract fun bindCalculateCheapestTotalFareUseCase(
        impl: CalculateCheapestTotalFareUseCaseImpl
    ): CalculateCheapestTotalFareUseCase
}
