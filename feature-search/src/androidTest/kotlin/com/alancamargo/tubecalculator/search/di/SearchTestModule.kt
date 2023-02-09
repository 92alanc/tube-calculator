package com.alancamargo.tubecalculator.search.di

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.di.CoreDesignModule
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
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
        SearchAnalyticsModule::class
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
}
