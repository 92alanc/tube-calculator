package com.alancamargo.tubecalculator.core.di

import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabaseImpl
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.log.LoggerImpl
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManagerImpl
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDataModule {

    @Provides
    @Singleton
    fun provideRemoteConfigManager(
        firebaseRemoteConfig: FirebaseRemoteConfig
    ): RemoteConfigManager {
        return RemoteConfigManagerImpl(firebaseRemoteConfig)
    }

    @Provides
    @Singleton
    fun provideRemoteDatabase(
        firebaseFirestore: FirebaseFirestore
    ): RemoteDatabase = RemoteDatabaseImpl(firebaseFirestore)

    @Provides
    @Singleton
    fun provideLogger(
        firebaseCrashlytics: FirebaseCrashlytics
    ): Logger = LoggerImpl(firebaseCrashlytics)
}
