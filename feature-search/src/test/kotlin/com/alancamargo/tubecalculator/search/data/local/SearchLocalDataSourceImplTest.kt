package com.alancamargo.tubecalculator.search.data.local

import com.alancamargo.tubecalculator.search.data.database.SearchDao
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
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
    fun `when database returns stations getAllStations should return Success`() {
        // GIVEN
        coEvery { mockDao.getAllStations() } returns stubDbStationList()

        // WHEN
        val actual = runBlocking { localDataSource.getAllStations() }

        // THEN
        val stations = stubStationList()
        val expected = StationListResult.Success(stations)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when database throws exception getAllStations should return GenericError`() {
        // GIVEN
        coEvery { mockDao.getAllStations() } throws IOException()

        // WHEN
        val actual = runBlocking { localDataSource.getAllStations() }

        // THEN
        val expected = StationListResult.GenericError
        assertThat(actual).isEqualTo(expected)
    }
}
