package com.alancamargo.tubecalculator.settings.di

import com.alancamargo.tubecalculator.settings.data.repository.SettingsRepositoryImpl
import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import com.alancamargo.tubecalculator.settings.domain.usecase.*
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.IsAdPersonalisationEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.IsAdPersonalisationEnabledUseCaseImpl
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.SetAdPersonalisationEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.SetAdPersonalisationEnabledUseCaseImpl
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.IsCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.IsCrashLoggingEnabledUseCaseImpl
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.SetCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.SetCrashLoggingEnabledUseCaseImpl
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

    @Binds
    @ViewModelScoped
    abstract fun bindIsAdPersonalisationEnabledUseCase(
        impl: IsAdPersonalisationEnabledUseCaseImpl
    ): IsAdPersonalisationEnabledUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSetAdPersonalisationEnabledUseCase(
        impl: SetAdPersonalisationEnabledUseCaseImpl
    ): SetAdPersonalisationEnabledUseCase
}
