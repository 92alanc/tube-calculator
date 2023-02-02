package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.fares.ui.navigation.FaresActivityNavigationImpl
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal abstract class FaresNavigationModule {

    @Binds
    @ActivityScoped
    abstract fun bindFaresActivityNavigation(
        impl: FaresActivityNavigationImpl
    ): FaresActivityNavigation
}
