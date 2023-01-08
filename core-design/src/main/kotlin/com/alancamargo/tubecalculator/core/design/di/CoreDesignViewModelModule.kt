package com.alancamargo.tubecalculator.core.design.di

import com.alancamargo.tubecalculator.core.design.text.BulletListFormatter
import com.alancamargo.tubecalculator.core.design.text.BulletListFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class CoreDesignViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindBulletListFormatter(impl: BulletListFormatterImpl): BulletListFormatter
}
