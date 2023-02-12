package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.fares.data.repository.FaresRepositoryImpl
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class FaresRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFaresRepository(impl: FaresRepositoryImpl): FaresRepository
}
