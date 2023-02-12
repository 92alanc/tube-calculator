package com.alancamargo.tubecalculator.home.di

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.di.CoreDesignModule
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.di.AppDataModule
import com.alancamargo.tubecalculator.core.di.AppVersionName
import com.alancamargo.tubecalculator.core.di.CoreDataModule
import com.alancamargo.tubecalculator.core.di.PreferencesModule
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.home.data.analytics.HomeAnalytics
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
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
        HomeAnalyticsModule::class,
        PreferencesModule::class,
        CoreDataModule::class,
        AppDataModule::class
    ]
)
internal object HomeTestModule {

    @Provides
    @Singleton
    fun provideMockAdLoader(): AdLoader = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockDialogueHelper(): DialogueHelper = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockFaresActivityNavigation(): FaresActivityNavigation = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockSettingsActivityNavigation(): SettingsActivityNavigation = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockHomeAnalytics(): HomeAnalytics = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockPreferencesManager(): PreferencesManager = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockLogger(): Logger = mockk(relaxed = true)

    @Provides
    @Singleton
    @AppVersionName
    fun provideAppVersionName(): String = "2023.1.0"

    @Provides
    @Singleton
    fun provideMockSearchActivityNavigation(): SearchActivityNavigation = mockk(relaxed = true)
}
