package com.alancamargo.tubecalculator.core.di

import android.content.Context
import com.alancamargo.tubecalculator.core.R
import com.alancamargo.tubecalculator.core.database.local.LocalDatabaseProvider
import com.alancamargo.tubecalculator.core.database.local.LocalDatabaseProviderImpl
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabaseImpl
import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.core.network.ApiProviderImpl
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManagerImpl
import com.alancamargo.tubecalculator.core.tools.Logger
import com.alancamargo.tubecalculator.core.tools.LoggerImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    fun provideApiProvider(impl: ApiProviderImpl): ApiProvider = impl

    @Provides
    @Singleton
    fun provideRemoteConfigManager(): RemoteConfigManager {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance().apply {
            setDefaultsAsync(R.xml.remote_config_defaults)
        }

        return RemoteConfigManagerImpl(firebaseRemoteConfig)
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseProvider(
        @ApplicationContext context: Context
    ): LocalDatabaseProvider = LocalDatabaseProviderImpl(context)

    @Provides
    @Singleton
    fun provideRemoteDatabase(): RemoteDatabase {
        val firestore = Firebase.firestore
        return RemoteDatabaseImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger = LoggerImpl()
}
