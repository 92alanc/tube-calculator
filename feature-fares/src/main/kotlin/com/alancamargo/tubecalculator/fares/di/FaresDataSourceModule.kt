package com.alancamargo.tubecalculator.fares.di

import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSource
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSourceImpl
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSourceImpl
import com.alancamargo.tubecalculator.fares.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FaresDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindFaresLocalDataSource(impl: FaresLocalDataSourceImpl): FaresLocalDataSource

    @Binds
    @Singleton
    abstract fun bindFaresRemoteDataSource(impl: FaresRemoteDataSourceImpl): FaresRemoteDataSource
}
