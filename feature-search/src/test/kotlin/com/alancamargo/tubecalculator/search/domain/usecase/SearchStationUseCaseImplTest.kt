package com.alancamargo.tubecalculator.search.domain.usecase

import app.cash.turbine.test
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.repository.SearchRepository
import com.alancamargo.tubecalculator.search.testtools.SEARCH_QUERY
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchStationUseCaseImplTest {

    private val mockRepository = mockk<SearchRepository>()
    private val useCase = SearchStationUseCaseImpl(mockRepository)

    @Test
    fun `invoke should get result from repository`() = runBlocking {
        // GIVEN
        val expected = StationListResult.GenericError
        every { mockRepository.searchStation(SEARCH_QUERY) } returns flowOf(expected)

        // WHEN
        val result = useCase.invoke(SEARCH_QUERY)

        // THEN
        result.test {
            val actual = awaitItem()
            Truth.assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }
}
