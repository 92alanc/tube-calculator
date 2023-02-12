package com.alancamargo.tubecalculator.core.di

import com.alancamargo.tubecalculator.core.analytics.Analytics
import com.alancamargo.tubecalculator.core.analytics.AnalyticsImpl
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreAnalyticsModule {

    @Provides
    @Singleton
    fun provideAnalytics(
        firebaseAnalytics: FirebaseAnalytics
    ): Analytics = AnalyticsImpl(firebaseAnalytics)
}
