package com.alancamargo.tubecalculator.search.di

import com.alancamargo.tubecalculator.search.data.remote.SearchRemoteDataSource
import com.alancamargo.tubecalculator.search.data.remote.SearchRemoteDataSourceImpl
import com.alancamargo.tubecalculator.search.data.repository.SearchRepositoryImpl
import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
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
    abstract fun bindSearchRemoteDataSource(
        impl: SearchRemoteDataSourceImpl
    ): SearchRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository
}
