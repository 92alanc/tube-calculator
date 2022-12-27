package com.alancamargo.tubecalculator.fares.data.remote

import app.cash.turbine.test
import com.alancamargo.tubecalculator.fares.data.service.FaresService
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.testtools.STATION_ID
import com.alancamargo.tubecalculator.fares.testtools.stubFareListRoot
import com.alancamargo.tubecalculator.fares.testtools.stubFareListRootResponse
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class FaresRemoteDataSourceImplTest {

    private val mockService = mockk<FaresService>()
    private val remoteDataSource = FaresRemoteDataSourceImpl(mockService)

    @Test
    fun `when service returns success getFares should return Success`() = runBlocking {
        // GIVEN
        coEvery {
            mockService.getFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(stubFareListRootResponse())

        // WHEN
        val station = stubStation()
        val result = remoteDataSource.getFares(origin = station, destination = station)

        // THEN
        result.test {
            val fareList = stubFareListRoot()
            val expected = FareListResult.Success(fareList)
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `when service returns null body getFares should return Empty`() = runBlocking {
        // GIVEN
        coEvery {
            mockService.getFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(null)

        // WHEN
        val station = stubStation()
        val result = remoteDataSource.getFares(origin = station, destination = station)

        // THEN
        result.test {
            val expected = FareListResult.Empty
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `when service returns empty list getFares should return Empty`() = runBlocking {
        // GIVEN
        coEvery {
            mockService.getFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(stubFareListRootResponse(emptyFareList = true))

        // WHEN
        val station = stubStation()
        val result = remoteDataSource.getFares(origin = station, destination = station)

        // THEN
        result.test {
            val expected = FareListResult.Empty
            val actual = awaitItem()
            assertThat(actual).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `when service returns request error getFares should return GenericError`() {
        runBlocking {
            // GIVEN
            val body = "".toResponseBody()
            coEvery {
                mockService.getFares(originId = STATION_ID, destinationId = STATION_ID)
            } returns Response.error(404, body)

            // WHEN
            val station = stubStation()
            val result = remoteDataSource.getFares(origin = station, destination = station)

            // THEN
            result.test {
                val expected = FareListResult.GenericError
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
                awaitComplete()
            }
        }
    }

    @Test
    fun `when service returns server error getFares should return ServerError`() {
        runBlocking {
            // GIVEN
            val body = "".toResponseBody()
            coEvery {
                mockService.getFares(originId = STATION_ID, destinationId = STATION_ID)
            } returns Response.error(500, body)

            // WHEN
            val station = stubStation()
            val result = remoteDataSource.getFares(origin = station, destination = station)

            // THEN
            result.test {
                val expected = FareListResult.ServerError
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
                awaitComplete()
            }
        }
    }

    @Test
    fun `when service returns random error getFares should return GenericError`() {
        runBlocking {
            // GIVEN
            val body = "".toResponseBody()
            coEvery {
                mockService.getFares(originId = STATION_ID, destinationId = STATION_ID)
            } returns Response.error(600, body)

            // WHEN
            val station = stubStation()
            val result = remoteDataSource.getFares(origin = station, destination = station)

            // THEN
            result.test {
                val expected = FareListResult.GenericError
                val actual = awaitItem()
                assertThat(actual).isEqualTo(expected)
                awaitComplete()
            }
        }
    }
}
