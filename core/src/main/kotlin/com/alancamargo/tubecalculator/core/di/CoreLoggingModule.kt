package com.alancamargo.tubecalculator.core.di

import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.log.LoggerImpl
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreLoggingModule {

    @Provides
    @Singleton
    fun provideLogger(
        firebaseCrashlytics: FirebaseCrashlytics
    ): Logger = LoggerImpl(firebaseCrashlytics)
}
