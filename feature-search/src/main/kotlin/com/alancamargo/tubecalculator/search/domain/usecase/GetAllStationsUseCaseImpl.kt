package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetAllStationsUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : GetAllStationsUseCase {

    override fun invoke(): Flow<StationListResult> {
        return repository.getAllStations()
    }
}
