package com.alancamargo.tubecalculator.fares.domain.usecase

import app.cash.turbine.test
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetFaresUseCaseImplTest {

    private val mockRepository = mockk<FaresRepository>()
    private val useCase = GetFaresUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get result from repository`() = runBlocking {
        // GIVEN
        val expected = FareListResult.Empty
        val station = stubStation()
        every {
            mockRepository.getFares(origin = station, destination = station)
        } returns flowOf(expected)

        // WHEN
        val result = useCase(origin = station, destination = station)

        // THEN
        result.test {
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }
}
