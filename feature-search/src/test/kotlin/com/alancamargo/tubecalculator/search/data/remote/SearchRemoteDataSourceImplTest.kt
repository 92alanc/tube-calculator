package com.alancamargo.tubecalculator.search.data.remote

import app.cash.turbine.test
import com.alancamargo.tubecalculator.search.data.model.StationSearchResultsResponse
import com.alancamargo.tubecalculator.search.data.service.SearchService
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.testtools.SEARCH_QUERY
import com.alancamargo.tubecalculator.search.testtools.stubSearchResultsResponse
import com.alancamargo.tubecalculator.search.testtools.stubStationList
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class SearchRemoteDataSourceImplTest {

    private val mockService = mockk<SearchService>()
    private val remoteDataSource = SearchRemoteDataSourceImpl(mockService)

    @Test
    fun `when service returns success searchStation should return Success`() = runBlocking {
        // GIVEN
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.success(stubSearchResultsResponse())

        // WHEN
        val result = remoteDataSource.searchStation(SEARCH_QUERY)

        // THEN
        result.test {
            val stations = stubStationList()
            val expected = StationListResult.Success(stations)
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `when service returns null body searchStation should return Empty`() = runBlocking {
        // GIVEN
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.success(null)

        // WHEN
        val result = remoteDataSource.searchStation(SEARCH_QUERY)

        // THEN
        result.test {
            val expected = StationListResult.Empty
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `when service returns no matches searchStation should return Empty`() = runBlocking {
        // GIVEN
        val body = StationSearchResultsResponse(matches = null)
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.success(body)

        // WHEN
        val result = remoteDataSource.searchStation(SEARCH_QUERY)

        // THEN
        result.test {
            val expected = StationListResult.Empty
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `when service returns request error searchStation should return GenericError`() {
        runBlocking {
            // GIVEN
            val body = "".toResponseBody()
            coEvery {
                mockService.searchStation(query = SEARCH_QUERY)
            } returns Response.error(404, body)

            // WHEN
            val result = remoteDataSource.searchStation(SEARCH_QUERY)

            // THEN
            result.test {
                val expected = StationListResult.GenericError
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
                awaitComplete()
            }
        }
    }

    @Test
    fun `when service returns server error searchStation should return ServerError`() {
        runBlocking {
            // GIVEN
            val body = "".toResponseBody()
            coEvery {
                mockService.searchStation(query = SEARCH_QUERY)
            } returns Response.error(500, body)

            // WHEN
            val result = remoteDataSource.searchStation(SEARCH_QUERY)

            // THEN
            result.test {
                val expected = StationListResult.ServerError
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
                awaitComplete()
            }
        }
    }

    @Test
    fun `when service returns random error searchStation should return GenericError`() {
        runBlocking {
            // GIVEN
            val body = "".toResponseBody()
            coEvery {
                mockService.searchStation(query = SEARCH_QUERY)
            } returns Response.error(600, body)

            // WHEN
            val result = remoteDataSource.searchStation(SEARCH_QUERY)

            // THEN
            result.test {
                val expected = StationListResult.GenericError
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
                awaitComplete()
            }
        }
    }
}
