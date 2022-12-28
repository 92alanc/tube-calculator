package com.alancamargo.tubecalculator.core.di

import com.alancamargo.tubecalculator.core.R
import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.core.network.ApiProviderImpl
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManagerImpl
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}
