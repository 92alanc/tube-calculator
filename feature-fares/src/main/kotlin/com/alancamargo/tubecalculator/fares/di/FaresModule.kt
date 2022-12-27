package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class FaresModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFaresRemoteDataSource(impl: FaresRemoteDataSourceImpl): FaresRemoteDataSource
}
