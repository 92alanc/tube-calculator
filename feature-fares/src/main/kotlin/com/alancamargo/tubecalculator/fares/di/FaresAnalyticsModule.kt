package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalytics
import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalyticsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class FaresAnalyticsModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFaresAnalytics(impl: FaresAnalyticsImpl): FaresAnalytics
}
