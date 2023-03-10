package com.alancamargo.tubecalculator.core.di

import android.content.Context
import com.alancamargo.tubecalculator.core.auth.AuthenticationManager
import com.alancamargo.tubecalculator.core.auth.AuthenticationManagerImpl
import com.alancamargo.tubecalculator.core.database.local.LocalDatabaseProvider
import com.alancamargo.tubecalculator.core.database.local.LocalDatabaseProviderImpl
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.network.ApiProvider
import com.alancamargo.tubecalculator.core.network.ApiProviderImpl
import com.alancamargo.tubecalculator.core.tools.FileHelper
import com.alancamargo.tubecalculator.core.tools.FileHelperImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoreModule {

    @Provides
    @Singleton
    fun provideApiProvider(
        @BaseUrl baseUrl: String
    ): ApiProvider = ApiProviderImpl(baseUrl)

    @Provides
    @Singleton
    fun provideLocalDatabaseProvider(
        @ApplicationContext context: Context
    ): LocalDatabaseProvider = LocalDatabaseProviderImpl(context)

    @Provides
    @Singleton
    fun provideAuthenticationManager(
        logger: Logger,
        firebaseAuth: FirebaseAuth
    ): AuthenticationManager {
        return AuthenticationManagerImpl(firebaseAuth, logger)
    }

    @Provides
    @Singleton
    fun provideFileHelper(
        @ApplicationContext context: Context
    ): FileHelper = FileHelperImpl(context)
}
