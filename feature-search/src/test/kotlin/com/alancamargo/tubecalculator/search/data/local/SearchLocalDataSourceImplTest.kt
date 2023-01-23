package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.search.data.database.SearchDao
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.testtools.SEARCH_QUERY
import com.alancamargo.tubecalculator.search.testtools.stubDbStationList
import com.alancamargo.tubecalculator.search.testtools.stubStationList
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

class SearchLocalDataSourceImplTest {

    private val mockDao = mockk<SearchDao>()
    private val localDataSource = SearchLocalDataSourceImpl(mockDao)

    @Test
    fun `when database returns stations searchStation should return Success`() {
        // GIVEN
        coEvery { mockDao.searchStation(SEARCH_QUERY) } returns stubDbStationList()

        // WHEN
        val actual = runBlocking { localDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val stations = stubStationList()
        val expected = StationListResult.Success(stations)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when database returns empty list searchStation should return Empty`() {
        // GIVEN
        coEvery { mockDao.searchStation(SEARCH_QUERY) } returns emptyList()

        // WHEN
        val actual = runBlocking { localDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val expected = StationListResult.Empty
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when database throws exception searchStation should return GenericError`() {
        // GIVEN
        coEvery { mockDao.searchStation(SEARCH_QUERY) } throws IOException()

        // WHEN
        val actual = runBlocking { localDataSource.searchStation(SEARCH_QUERY) }

        // THEN
        val expected = StationListResult.GenericError
        assertThat(actual).isEqualTo(expected)
    }
}
