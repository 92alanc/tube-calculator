package com.alancamargo.tubecalculator.search.di

import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.di.CoreDesignModule
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.di.AppDataModule
import com.alancamargo.tubecalculator.core.di.AppVersionName
import com.alancamargo.tubecalculator.core.di.CoreDataModule
import com.alancamargo.tubecalculator.core.di.PreferencesModule
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.alancamargo.tubecalculator.search.data.database.SearchDao
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
        SearchAnalyticsModule::class,
        PreferencesModule::class,
        CoreDataModule::class,
        AppDataModule::class,
        SearchDataModule::class
    ]
)
internal object SearchTestModule {

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
    fun provideMockSearchAnalytics(): SearchAnalytics = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockPreferencesManager(): PreferencesManager = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockRemoteConfigManager(): RemoteConfigManager = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockRemoteDatabase(): RemoteDatabase = mockk()

    @Provides
    @Singleton
    fun provideMockLogger(): Logger = mockk(relaxed = true)

    @Provides
    @Singleton
    @AppVersionName
    fun provideAppVersionName(): String = "2023.1.0"

    @Provides
    @Singleton
    fun provideMockSearchDao(): SearchDao = mockk()
}
