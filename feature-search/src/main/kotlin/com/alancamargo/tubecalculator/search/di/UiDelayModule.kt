package com.alancamargo.tubecalculator.search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UiDelayModule {

    private const val UI_DELAY_MILLIS = 200L

    @Provides
    @Singleton
    @UiDelay
    fun provideUiDelay(): Long = UI_DELAY_MILLIS
}
