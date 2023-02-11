package com.alancamargo.tubecalculator.core.di

import android.content.Context
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.core.preferences.PreferencesManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext context: Context
    ): PreferencesManager = PreferencesManagerImpl(context)
}
