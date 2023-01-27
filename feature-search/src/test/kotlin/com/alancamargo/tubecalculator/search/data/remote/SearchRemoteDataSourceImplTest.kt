package com.alancamargo.tubecalculator.search.data.remote

import com.alancamargo.tubecalculator.search.data.api.SearchService
import com.alancamargo.tubecalculator.search.data.model.StationSearchResultsResponse
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

class SearchRemoteDataSourceImplTest {

    private val mockService = mockk<SearchService>()
    private val remoteDataSource = SearchRemoteDataSourceImpl(mockService)

    @Test
    fun `when service returns stations searchStation should return Success`() {
        // GIVEN
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.success(stubSearchResultsResponse())

        // WHEN
        val actual = runBlocking { remoteDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val stations = stubStationList()
        val expected = StationListResult.Success(stations)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns empty list searchStation should return Empty`() {
        // GIVEN
        val body = StationSearchResultsResponse(matches = emptyList())
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.success(body)

        // WHEN
        val actual = runBlocking { remoteDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val expected = StationListResult.Empty
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns no matches searchStation should return Empty`() {
        // GIVEN
        val body = StationSearchResultsResponse(matches = null)
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.success(body)

        // WHEN
        val actual = runBlocking { remoteDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val expected = StationListResult.Empty
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns request error searchStation should return GenericError`() {
        // GIVEN
        val body = "".toResponseBody()
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.error(404, body)

        // WHEN
        val actual = runBlocking { remoteDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val expected = StationListResult.GenericError
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns server error searchStation should return ServerError`() {
        // GIVEN
        val body = "".toResponseBody()
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.error(500, body)

        // WHEN
        val actual = runBlocking { remoteDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val expected = StationListResult.ServerError
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns random error searchStation should return GenericError`() {
        // GIVEN
        val body = "".toResponseBody()
        coEvery {
            mockService.searchStation(query = SEARCH_QUERY)
        } returns Response.error(600, body)

        // WHEN
        val actual = runBlocking { remoteDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val expected = StationListResult.GenericError
        assertThat(actual).isEqualTo(expected)
    }
}
