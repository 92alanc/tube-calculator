package com.alancamargo.tubecalculator.home.di

import com.alancamargo.tubecalculator.home.ui.navigation.HomeActivityNavigationImpl
import com.alancamargo.tubecalculator.navigation.HomeActivityNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal abstract class HomeNavigationModule {

    @Binds
    @ActivityScoped
    abstract fun bindHomeActivityNavigation(
        impl: HomeActivityNavigationImpl
    ): HomeActivityNavigation
}
