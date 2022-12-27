package com.alancamargo.tubecalculator.search.data.repository

import app.cash.turbine.test
import com.alancamargo.tubecalculator.search.data.remote.SearchRemoteDataSource
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.testtools.SEARCH_QUERY
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchRepositoryImplTest {

    private val mockRemoteDataSource = mockk<SearchRemoteDataSource>()
    private val repository = SearchRepositoryImpl(mockRemoteDataSource)

    @Test
    fun `searchStation should get result from remote data source`() = runBlocking {
        // GIVEN
        val expected = StationListResult.Empty
        every { mockRemoteDataSource.searchStation(SEARCH_QUERY) } returns flowOf(expected)

        // WHEN
        val result = repository.searchStation(SEARCH_QUERY)

        // THEN
        result.test {
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }
}
