package com.alancamargo.tubecalculator.core.di

import android.content.Context
import com.alancamargo.tubecalculator.core.analytics.Analytics
import com.alancamargo.tubecalculator.core.analytics.AnalyticsImpl
import com.alancamargo.tubecalculator.core.auth.AuthenticationManager
import com.alancamargo.tubecalculator.core.auth.AuthenticationManagerImpl
import com.alancamargo.tubecalculator.core.database.local.LocalDatabaseProvider
import com.alancamargo.tubecalculator.core.database.local.LocalDatabaseProviderImpl
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabaseImpl
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.core.network.ApiProviderImpl
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.core.preferences.PreferencesManagerImpl
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManagerImpl
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoreModule {

    @Provides
    @Singleton
    fun provideApiProvider(
        @BaseUrl baseUrl: String
    ): ApiProvider = ApiProviderImpl(baseUrl)

    @Provides
    @Singleton
    fun provideRemoteConfigManager(firebaseRemoteConfig: FirebaseRemoteConfig): RemoteConfigManager {
        return RemoteConfigManagerImpl(firebaseRemoteConfig)
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseProvider(
        @ApplicationContext context: Context
    ): LocalDatabaseProvider = LocalDatabaseProviderImpl(context)

    @Provides
    @Singleton
    fun provideRemoteDatabase(
        firebaseFirestore: FirebaseFirestore
    ): RemoteDatabase = RemoteDatabaseImpl(firebaseFirestore)

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext context: Context
    ): PreferencesManager = PreferencesManagerImpl(context)

    @Provides
    @Singleton
    fun provideAnalytics(
        firebaseAnalytics: FirebaseAnalytics
    ): Analytics = AnalyticsImpl(firebaseAnalytics)

    @Provides
    @Singleton
    fun provideAuthenticationManager(
        logger: Logger,
        firebaseAuth: FirebaseAuth
    ): AuthenticationManager {
        return AuthenticationManagerImpl(firebaseAuth, logger)
    }
}
