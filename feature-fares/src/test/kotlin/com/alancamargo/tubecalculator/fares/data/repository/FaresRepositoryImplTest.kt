package com.alancamargo.tubecalculator.fares.data.repository

import app.cash.turbine.test
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSource
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.testtools.stubFareListRoot
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal

private const val KEY = "bus_and_tram_base_fare"

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
    fun `when local data source returns success getFares should not call remote`() = runBlocking {
        // GIVEN
        val body = listOf(stubFareListRoot())
        val expected = FareListResult.Success(body)
        val station = stubStation()
        coEvery {
            mockLocalDataSource.getFares(origin = station, destination = station)
        } returns expected

        // WHEN
        val result = repository.getFares(origin = station, destination = station)

        // THEN
        result.test {
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }

        coVerify(exactly = 0) {
            mockRemoteDataSource.getFares(origin = any(), destination = any())
        }
    }

    @Test
    fun `when local data source throws exception getFares should get result from remote`() = runBlocking {
        // GIVEN
        val body = listOf(stubFareListRoot())
        val expected = FareListResult.Success(body)
        val station = stubStation()
        coEvery {
            mockLocalDataSource.getFares(origin = station, destination = station)
        } throws Throwable()
        coEvery {
            mockRemoteDataSource.getFares(origin = station, destination = station)
        } returns expected

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
    fun `when local data source throws exception getFares should update local`() = runBlocking {
        // GIVEN
        val body = listOf(stubFareListRoot())
        val expected = FareListResult.Success(body)
        val station = stubStation()
        coEvery {
            mockLocalDataSource.getFares(origin = station, destination = station)
        } throws Throwable()
        coEvery {
            mockRemoteDataSource.getFares(origin = station, destination = station)
        } returns expected

        // WHEN
        val result = repository.getFares(origin = station, destination = station)

        // THEN
        result.test {
            awaitItem()
            awaitComplete()
        }

        coVerify {
            mockLocalDataSource.saveFares(origin = station, destination = station, fares = body)
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
