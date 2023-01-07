package com.alancamargo.tubecalculator.core.design.di

import com.alancamargo.tubecalculator.core.design.tools.AdLoader
import com.alancamargo.tubecalculator.core.design.tools.AdLoaderImpl
import com.alancamargo.tubecalculator.core.design.tools.DialogueHelper
import com.alancamargo.tubecalculator.core.design.tools.DialogueHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
internal abstract class CoreDesignModule {

    @Binds
    @ActivityScoped
    abstract fun bindDialogueHelper(impl: DialogueHelperImpl): DialogueHelper

    @Binds
    @ActivityScoped
    abstract fun bindAdLoader(impl: AdLoaderImpl): AdLoader
}
