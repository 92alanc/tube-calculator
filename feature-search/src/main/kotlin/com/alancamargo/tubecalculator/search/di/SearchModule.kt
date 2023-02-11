package com.alancamargo.tubecalculator.search.di

import com.alancamargo.tubecalculator.search.data.local.SearchLocalDataSource
import com.alancamargo.tubecalculator.search.data.local.SearchLocalDataSourceImpl
import com.alancamargo.tubecalculator.search.data.repository.SearchRepositoryImpl
import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
import com.alancamargo.tubecalculator.search.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class SearchModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @ViewModelScoped
    abstract fun bindGetMinQueryLengthUseCase(
        impl: GetMinQueryLengthUseCaseImpl
    ): GetMinQueryLengthUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSearchLocalDataSource(impl: SearchLocalDataSourceImpl): SearchLocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindGetAllStationsUseCase(impl: SearchStationUseCaseImpl): SearchStationUseCase
}
