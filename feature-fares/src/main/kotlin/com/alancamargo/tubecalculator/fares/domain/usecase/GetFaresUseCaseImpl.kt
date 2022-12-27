package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetFaresUseCaseImpl @Inject constructor(
    private val repository: FaresRepository
) : GetFaresUseCase {

    override fun invoke(origin: Station, destination: Station): Flow<FareListResult> {
        return repository.getFares(origin, destination)
    }
}
