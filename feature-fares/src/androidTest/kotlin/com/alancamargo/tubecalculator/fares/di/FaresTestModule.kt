package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.di.CoreDesignModule
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.di.BaseUrl
import com.alancamargo.tubecalculator.core.di.BaseUrlModule
import com.alancamargo.tubecalculator.core.di.CoreDataModule
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.test.web.mockWebServer
import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalytics
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [
        CoreDesignModule::class,
        CoreDataModule::class,
        FaresAnalyticsModule::class,
        BaseUrlModule::class
    ]
)
internal object FaresTestModule {

    @Provides
    @Singleton
    fun provideMockAdLoader(): AdLoader = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockDialogueHelper(): DialogueHelper = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockSearchActivityNavigation(): SearchActivityNavigation {
        return mockk(relaxed = true)
    }

    @Provides
    @Singleton
    fun provideMockFaresAnalytics(): FaresAnalytics = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockLogger(): Logger = mockk(relaxed = true)

    @Provides
    @Singleton
    @BaseUrl
    fun provideMockBaseUrl(): String = runBlocking(Dispatchers.IO) {
        mockWebServer.url("/").toString()
    }

    @Provides
    @Singleton
    fun provideMockRemoteConfigManager(): RemoteConfigManager = mockk()

    @Provides
    @Singleton
    fun provideMockRemoteDatabase(): RemoteDatabase = mockk(relaxed = true)
}
