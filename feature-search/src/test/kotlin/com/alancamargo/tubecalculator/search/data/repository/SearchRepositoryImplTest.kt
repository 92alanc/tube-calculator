package com.alancamargo.tubecalculator.search.data.repository

import app.cash.turbine.test
import com.alancamargo.tubecalculator.search.data.local.SearchLocalDataSource
import com.alancamargo.tubecalculator.search.data.remote.SearchRemoteDataSource
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.testtools.SEARCH_QUERY
import com.alancamargo.tubecalculator.search.testtools.stubStationList
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchRepositoryImplTest {

    private val mockRemoteDataSource = mockk<SearchRemoteDataSource>()
    private val mockLocalDataSource = mockk<SearchLocalDataSource>()
    private val repository = SearchRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)

    @Test
    fun `when local returns Success searchStation should return Success`() {
        runBlocking {
            // GIVEN
            val expected = StationListResult.Success(stubStationList())
            coEvery { mockLocalDataSource.searchStation(SEARCH_QUERY) } returns expected

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

    @Test
    fun `when local returns Success searchStation should not call remote`() {
        // GIVEN
        val expected = StationListResult.Success(stubStationList())
        coEvery { mockLocalDataSource.searchStation(SEARCH_QUERY) } returns expected

        // WHEN
        repository.searchStation(SEARCH_QUERY)

        // THEN
        coVerify(exactly = 0) { mockRemoteDataSource.searchStation(query = any()) }
    }

    @Test
    fun `when local does not return Success searchStation should get result from remote data source`() {
        runBlocking {
            // GIVEN
            coEvery {
                mockLocalDataSource.searchStation(SEARCH_QUERY)
            } returns StationListResult.ServerError
            val expected = StationListResult.Success(stubStationList())
            coEvery { mockRemoteDataSource.searchStation(SEARCH_QUERY) } returns expected

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
}
