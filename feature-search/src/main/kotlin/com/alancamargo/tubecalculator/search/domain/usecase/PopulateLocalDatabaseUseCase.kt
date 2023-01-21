package com.alancamargo.tubecalculator.search.domain.usecase

import kotlinx.coroutines.flow.Flow

internal interface PopulateLocalDatabaseUseCase {

    operator fun invoke(): Flow<Unit>
}
