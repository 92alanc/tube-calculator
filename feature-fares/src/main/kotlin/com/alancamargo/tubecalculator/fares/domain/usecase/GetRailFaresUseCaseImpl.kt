package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetRailFaresUseCaseImpl @Inject constructor(
    private val repository: FaresRepository
) : GetRailFaresUseCase {

    override fun invoke(origin: Station, destination: Station): Flow<RailFaresResult> {
        return repository.getRailFares(origin, destination)
    }
}
