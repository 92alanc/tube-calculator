package com.alancamargo.tubecalculator.fares.data.repository

import app.cash.turbine.test
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSource
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import com.alancamargo.tubecalculator.fares.testtools.stubRailFare
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal

private const val KEY_BUS_AND_TRAM_BASE_FARE = "bus_and_tram_base_fare"
private const val KEY_BUS_AND_TRAM_FARE_DAILY_CAP = "bus_and_tram_fare_daily_cap"

class FaresRepositoryImplTest {

    private val mockRemoteDataSource = mockk<FaresRemoteDataSource>()
    private val mockRemoteConfigManager = mockk<RemoteConfigManager>()
    private val mockLocalDataSource = mockk<FaresLocalDataSource>(relaxed = true)
    private val repository = FaresRepositoryImpl(
        mockRemoteDataSource,
        mockRemoteConfigManager,
        mockLocalDataSource
    )

    @Test
    fun `when local data source returns success getRailFares should not call remote`() = runBlocking {
        // GIVEN
        val body = listOf(stubRailFare())
        val expected = RailFaresResult.Success(body)
        val station = stubStation()
        coEvery {
            mockLocalDataSource.getRailFares(origin = station, destination = station)
        } returns expected

        // WHEN
        val result = repository.getRailFares(origin = station, destination = station)

        // THEN
        result.test {
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }

        coVerify(exactly = 0) {
            mockRemoteDataSource.getRailFares(origin = any(), destination = any())
        }
    }

    @Test
    fun `when local data source throws exception getRailFares should get result from remote`() = runBlocking {
        // GIVEN
        val body = listOf(stubRailFare())
        val expected = RailFaresResult.Success(body)
        val station = stubStation()
        coEvery {
            mockLocalDataSource.getRailFares(origin = station, destination = station)
        } throws Throwable()
        coEvery {
            mockRemoteDataSource.getRailFares(origin = station, destination = station)
        } returns expected

        // WHEN
        val result = repository.getRailFares(origin = station, destination = station)

        // THEN
        result.test {
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `when local data source throws exception getRailFares should update local`() = runBlocking {
        // GIVEN
        val body = listOf(stubRailFare())
        val expected = RailFaresResult.Success(body)
        val station = stubStation()
        coEvery {
            mockLocalDataSource.getRailFares(origin = station, destination = station)
        } throws Throwable()
        coEvery {
            mockRemoteDataSource.getRailFares(origin = station, destination = station)
        } returns expected

        // WHEN
        val result = repository.getRailFares(origin = station, destination = station)

        // THEN
        result.test {
            awaitItem()
            awaitComplete()
        }

        coVerify {
            mockLocalDataSource.saveRailFares(origin = station, destination = station, railFares = body)
        }
    }

    @Test
    fun `getBusAndTramBaseFare should get base fare from remote config`() {
        // GIVEN
        val expected = 1.65
        every { mockRemoteConfigManager.getDouble(KEY_BUS_AND_TRAM_BASE_FARE) } returns expected

        // WHEN
        val actual = repository.getBusAndTramBaseFare()

        // THEN
        assertThat(actual).isEqualTo(BigDecimal.valueOf(expected))
    }

    @Test
    fun `getBusAndTramFareDailyCap should get daily cap from remote config`() {
        // GIVEN
        val expected = 4.95
        every {
            mockRemoteConfigManager.getDouble(KEY_BUS_AND_TRAM_FARE_DAILY_CAP)
        } returns expected

        // WHEN
        val actual = repository.getBusAndTramDailyFareCap()

        // THEN
        assertThat(actual).isEqualTo(BigDecimal.valueOf(expected))
    }
}
