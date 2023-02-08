package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSource
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSourceImpl
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSourceImpl
import com.alancamargo.tubecalculator.fares.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class FaresDataSourceModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFaresLocalDataSource(impl: FaresLocalDataSourceImpl): FaresLocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindFaresRemoteDataSource(impl: FaresRemoteDataSourceImpl): FaresRemoteDataSource
}
