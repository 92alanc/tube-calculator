package com.alancamargo.tubecalculator.home.di

import com.alancamargo.tubecalculator.home.domain.usecase.*
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

    @Binds
    @ViewModelScoped
    abstract fun bindShouldShowDeleteJourneyTutorialUseCase(
        impl: ShouldShowDeleteJourneyTutorialUseCaseImpl
    ): ShouldShowDeleteJourneyTutorialUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDisableDeleteJourneyTutorialUseCase(
        impl: DisableDeleteJourneyTutorialUseCaseImpl
    ): DisableDeleteJourneyTutorialUseCase
}
