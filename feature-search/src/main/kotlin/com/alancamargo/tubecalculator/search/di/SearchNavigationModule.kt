package com.alancamargo.tubecalculator.search.di

import android.content.Context
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.search.ui.navigation.SearchActivityNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal object SearchNavigationModule {

    @Provides
    @ActivityScoped
    fun provideSearchActivityNavigation(
        impl: SearchActivityNavigationImpl
    ): SearchActivityNavigation = impl

    @Provides
    @ActivityScoped
    fun provideFaresActivityNavigation(): FaresActivityNavigation {
        return object : FaresActivityNavigation {
            override fun startActivity(
                context: Context,
                origin: UiStation,
                destination: UiStation,
                busAndTramJourneyCount: Int
            ) {
                // TODO: remove when real implementation is ready
            }
        }
    }
}
