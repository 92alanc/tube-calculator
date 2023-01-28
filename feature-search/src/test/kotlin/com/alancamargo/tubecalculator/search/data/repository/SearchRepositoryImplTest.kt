package com.alancamargo.tubecalculator.search.data.repository

import app.cash.turbine.test
import com.alancamargo.tubecalculator.search.data.local.SearchLocalDataSource
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.testtools.stubStationList
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchRepositoryImplTest {

    private val mockLocalDataSource = mockk<SearchLocalDataSource>()
    private val repository = SearchRepositoryImpl(mockLocalDataSource)

    @Test
    fun `getAllStations should return result from local`() {
        runBlocking {
            // GIVEN
            val expected = StationListResult.Success(stubStationList())
            coEvery { mockLocalDataSource.getAllStations() } returns expected

            // WHEN
            val result = repository.getAllStations()

            // THEN
            result.test {
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
                awaitComplete()
            }
        }
    }
}
