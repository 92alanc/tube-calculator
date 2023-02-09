package com.alancamargo.tubecalculator.search.di

import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalyticsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class SearchAnalyticsModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSearchAnalytics(impl: SearchAnalyticsImpl): SearchAnalytics
}
