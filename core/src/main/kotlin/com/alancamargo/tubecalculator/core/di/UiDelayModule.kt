package com.alancamargo.tubecalculator.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiDelayModule {

    private const val UI_DELAY_MILLIS = 200L

    @Provides
    @Singleton
    @UiDelay
    fun provideUiDelay(): Long = UI_DELAY_MILLIS
}
