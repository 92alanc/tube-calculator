package com.alancamargo.tubecalculator.home.di

import com.alancamargo.tubecalculator.home.domain.usecase.DisableFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.DisableFirstAccessUseCaseImpl
import com.alancamargo.tubecalculator.home.domain.usecase.IsFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.IsFirstAccessUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class HomeModule {

    @Binds
    @ViewModelScoped
    abstract fun bindIsFirstAccessUseCase(impl: IsFirstAccessUseCaseImpl): IsFirstAccessUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDisableFirstAccessUseCase(
        impl: DisableFirstAccessUseCaseImpl
    ): DisableFirstAccessUseCase
}
