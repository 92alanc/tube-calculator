package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.di.CoreDesignModule
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.di.FirebaseModule
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoreDesignModule::class, FirebaseModule::class]
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
    fun provideMockFirebaseRemoteConfig(): FirebaseRemoteConfig = mockk()

    @Provides
    @Singleton
    fun provideMockFirebaseAnalytics(): FirebaseAnalytics = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideMockFirebaseCrashlytics(): FirebaseCrashlytics = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = mockk(relaxed = true)
}
