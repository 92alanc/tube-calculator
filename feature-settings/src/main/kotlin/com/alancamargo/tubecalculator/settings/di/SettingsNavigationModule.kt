package com.alancamargo.tubecalculator.settings.di

import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import com.alancamargo.tubecalculator.settings.ui.navigation.SettingsActivityNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal abstract class SettingsNavigationModule {

    @Binds
    @ActivityScoped
    abstract fun bindSettingsActivityNavigation(
        impl: SettingsActivityNavigationImpl
    ): SettingsActivityNavigation
}
