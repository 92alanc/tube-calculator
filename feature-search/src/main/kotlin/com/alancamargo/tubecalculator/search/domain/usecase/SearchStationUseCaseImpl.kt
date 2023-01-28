package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SearchStationUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchStationUseCase {

    override fun invoke(query: String): Flow<StationListResult> {
        return repository.searchStation(query)
    }
}
