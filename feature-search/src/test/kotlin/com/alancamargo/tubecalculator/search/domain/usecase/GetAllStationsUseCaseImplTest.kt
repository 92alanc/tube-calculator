package com.alancamargo.tubecalculator.search.domain.usecase

import app.cash.turbine.test
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetAllStationsUseCaseImplTest {

    private val mockRepository = mockk<SearchRepository>()
    private val useCase = GetAllStationsUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get result from repository`() = runBlocking {
        // GIVEN
        val expected = StationListResult.GenericError
        every { mockRepository.getAllStations() } returns flowOf(expected)

        // WHEN
        val result = useCase()

        // THEN
        result.test {
            val actual = awaitItem()
            Truth.assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }
}
