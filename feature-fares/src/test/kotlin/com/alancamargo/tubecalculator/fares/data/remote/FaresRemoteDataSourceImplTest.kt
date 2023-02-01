package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.fares.data.model.database.RemoteDatabaseFareListWrapper
import com.alancamargo.tubecalculator.fares.data.service.RailFaresService
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import com.alancamargo.tubecalculator.fares.testtools.STATION_ID
import com.alancamargo.tubecalculator.fares.testtools.stubRailFare
import com.alancamargo.tubecalculator.fares.testtools.stubRailFareResponse
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

private const val REMOTE_DATABASE_COLLECTION_NAME = "fares"

class FaresRemoteDataSourceImplTest {

    private val mockService = mockk<RailFaresService>()
    private val mockRemoteDatabase = mockk<RemoteDatabase>(relaxed = true)
    private val remoteDataSource = FaresRemoteDataSourceImpl(mockService, mockRemoteDatabase)

    @Test
    fun `when remote database returns fares getRailFares should not call service`() {
        // GIVEN
        val documentId = "$STATION_ID#$STATION_ID"
        coEvery {
            mockRemoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns RemoteDatabaseFareListWrapper(listOf(stubRailFareResponse()))

        // WHEN
        val station = stubStation()
        val actual = runBlocking {
            remoteDataSource.getRailFares(origin = station, destination = station)
        }

        // THEN
        val fareList = stubRailFare()
        val expected = RailFaresResult.Success(listOf(fareList))
        assertThat(actual).isEqualTo(expected)
        coVerify(exactly = 0) { mockService.getRailFares(originId = any(), destinationId = any()) }
    }

    @Test
    fun `when remote database returns null getRailFares should call service`() {
        // GIVEN
        val documentId = "$STATION_ID#$STATION_ID"
        coEvery {
            mockRemoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(null)

        // WHEN
        val station = stubStation()
        runBlocking { remoteDataSource.getRailFares(origin = station, destination = station) }

        // THEN
        coVerify { mockService.getRailFares(originId = any(), destinationId = any()) }
    }

    @Test
    fun `when remote database throws exception getRailFares should call service`() {
        // GIVEN
        val documentId = "$STATION_ID#$STATION_ID"
        coEvery {
            mockRemoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } throws Throwable()
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(null)

        // WHEN
        val station = stubStation()
        runBlocking { remoteDataSource.getRailFares(origin = station, destination = station) }

        // THEN
        coVerify { mockService.getRailFares(originId = any(), destinationId = any()) }
    }

    @Test
    fun `when service returns success getRailFares should return Success`() {
        // GIVEN
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(listOf(stubRailFareResponse()))

        // WHEN
        val station = stubStation()
        val actual = runBlocking {
            remoteDataSource.getRailFares(origin = station, destination = station)
        }

        // THEN
        val fareList = stubRailFare()
        val expected = RailFaresResult.Success(listOf(fareList))
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns success getRailFares should save data to remote database`() {
        // GIVEN
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
        val expected = listOf(stubRailFareResponse())
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(expected)

        // WHEN
        val station = stubStation()
        runBlocking { remoteDataSource.getRailFares(origin = station, destination = station) }

        // THEN
        val documentId = "$STATION_ID#$STATION_ID"
        coVerify {
            mockRemoteDatabase.save(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                data = RemoteDatabaseFareListWrapper(expected)
            )
        }
    }

    @Test
    fun `when service returns null body getRailFares should return GenericError`() {
        // GIVEN
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(null)

        // WHEN
        val station = stubStation()
        val actual = runBlocking {
            remoteDataSource.getRailFares(origin = station, destination = station)
        }

        // THEN
        val expected = RailFaresResult.GenericError
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns empty list getRailFares should return InvalidQueryError`() {
        // GIVEN
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.success(emptyList())

        // WHEN
        val station = stubStation()
        val actual = runBlocking {
            remoteDataSource.getRailFares(origin = station, destination = station)
        }

        // THEN
        val expected = RailFaresResult.InvalidQueryError
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns request error getRailFares should return GenericError`() {
        // GIVEN
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
        val body = "".toResponseBody()
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.error(404, body)

        // WHEN
        val station = stubStation()
        val actual = runBlocking {
            remoteDataSource.getRailFares(origin = station, destination = station)
        }

        // THEN
        val expected = RailFaresResult.GenericError
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns server error getRailFares should return ServerError`() {
        // GIVEN
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
        val body = "".toResponseBody()
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.error(500, body)

        // WHEN
        val station = stubStation()
        val actual = runBlocking {
            remoteDataSource.getRailFares(origin = station, destination = station)
        }

        // THEN
        val expected = RailFaresResult.ServerError
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when service returns random error getRailFares should return GenericError`() {
        // GIVEN
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
        val body = "".toResponseBody()
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.error(600, body)

        // WHEN
        val station = stubStation()
        val actual = runBlocking {
            remoteDataSource.getRailFares(origin = station, destination = station)
        }

        // THEN
        val expected = RailFaresResult.GenericError
        assertThat(actual).isEqualTo(expected)
    }
}
