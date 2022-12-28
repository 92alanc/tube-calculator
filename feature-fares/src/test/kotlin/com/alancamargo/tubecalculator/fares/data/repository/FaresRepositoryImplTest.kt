package com.alancamargo.tubecalculator.fares.data.repository

import app.cash.turbine.test
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal

private const val KEY = "bus_and_tram_base_fare"

class FaresRepositoryImplTest {

    private val mockRemoteDataSource = mockk<FaresRemoteDataSource>()
    private val mockRemoteConfigManager = mockk<RemoteConfigManager>()
    private val repository = FaresRepositoryImpl(mockRemoteDataSource, mockRemoteConfigManager)

    @Test
    fun `getFares should get result from remote data source`() = runBlocking {
        // GIVEN
        val expected = FareListResult.GenericError
        val station = stubStation()
        every {
            mockRemoteDataSource.getFares(origin = station, destination = station)
        } returns flowOf(expected)

        // WHEN
        val result = repository.getFares(origin = station, destination = station)

        // THEN
        result.test {
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `getBusAndTramBaseFare should get base fare from remote config`() {
        // GIVEN
        val expected = 1.65
        every { mockRemoteConfigManager.getDouble(KEY) } returns expected

        // WHEN
        val actual = repository.getBusAndTramBaseFare()

        // THEN
        assertThat(actual).isEqualTo(BigDecimal.valueOf(expected))
    }
}
