package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.di.CoreDesignModule
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.di.CoreLoggingModule
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalytics
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
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
        CoreLoggingModule::class,
        FaresRepositoryModule::class,
        FaresAnalyticsModule::class
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
    fun provideMockFaresRepository(): FaresRepository = mockk()

    @Provides
    @Singleton
    fun provideMockFaresAnalytics(): FaresAnalytics = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockLogger(): Logger = mockk(relaxed = true)
}
