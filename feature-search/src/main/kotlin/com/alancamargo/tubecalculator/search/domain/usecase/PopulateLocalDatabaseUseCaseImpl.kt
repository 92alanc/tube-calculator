package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class PopulateLocalDatabaseUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : PopulateLocalDatabaseUseCase {

    override fun invoke(): Flow<Unit> {
        return repository.populateDatabase()
    }
}
