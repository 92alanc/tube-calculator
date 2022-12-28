package com.alancamargo.tubecalculator.search.di

import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.search.ui.navigation.SearchActivityNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal abstract class SearchNavigationModule {

    @Binds
    @ActivityScoped
    abstract fun bindSearchActivityNavigation(
        impl: SearchActivityNavigationImpl
    ): SearchActivityNavigation
}
