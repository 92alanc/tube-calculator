package com.alancamargo.tubecalculator.settings.di

import com.alancamargo.tubecalculator.core.analytics.Analytics
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.di.CoreDesignModule
import com.alancamargo.tubecalculator.core.di.CoreAnalyticsModule
import com.alancamargo.tubecalculator.core.di.CoreDataModule
import com.alancamargo.tubecalculator.core.log.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [
        CoreDesignModule::class,
        CoreAnalyticsModule::class,
        CoreDataModule::class
    ]
)
internal object SettingsTestModule {

    @Provides
    @Singleton
    fun provideMockAdLoader(): AdLoader = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockAnalytics(): Analytics = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockLogger(): Logger = mockk(relaxed = true)
}