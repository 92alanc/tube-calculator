package com.alancamargo.tubecalculator.settings.di

import com.alancamargo.tubecalculator.settings.data.repository.SettingsRepositoryImpl
import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import com.alancamargo.tubecalculator.settings.domain.usecase.IsCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.IsCrashLoggingEnabledUseCaseImpl
import com.alancamargo.tubecalculator.settings.domain.usecase.SetCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.SetCrashLoggingEnabledUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class SettingsModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSetCrashLoggingEnabledUseCase(
        impl: SetCrashLoggingEnabledUseCaseImpl
    ): SetCrashLoggingEnabledUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindIsCrashLoggingEnabledUseCase(
        impl: IsCrashLoggingEnabledUseCaseImpl
    ): IsCrashLoggingEnabledUseCase
}
