package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.fares.data.model.database.RemoteDatabaseFareListWrapper
import com.alancamargo.tubecalculator.fares.data.model.responses.RailFareResponse
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
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

private const val REMOTE_DATABASE_COLLECTION_NAME = "fares"

class FaresRemoteDataSourceImplTest {

    private val mockService = mockk<RailFaresService>()
    private val mockRemoteDatabase = mockk<RemoteDatabase>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val remoteDataSource = FaresRemoteDataSourceImpl(
        mockService,
        mockRemoteDatabase,
        mockLogger
    )

    @Test
    fun `when service returns fares getRailFares should not call remote database`() {
        // GIVEN
        coEvery {
            mockService.getRailFares(
                originId = STATION_ID,
                destinationId = STATION_ID
            )
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
        coVerify(exactly = 0) {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        }
    }

    @Test
    fun `when service returns null body getRailFares should call remote database`() {
        // GIVEN
        val documentId = "$STATION_ID#$STATION_ID"
        coEvery {
            mockService.getRailFares(
                originId = STATION_ID,
                destinationId = STATION_ID
            )
        } returns Response.success(null)
        coEvery {
            mockRemoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns RemoteDatabaseFareListWrapper(listOf(stubRailFareResponse()))

        // WHEN
        val station = stubStation()
        runBlocking { remoteDataSource.getRailFares(origin = station, destination = station) }

        // THEN
        coVerify {
            mockRemoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        }
    }

    @Test
    fun `when service throws exception getRailFares should call remote database`() {
        // GIVEN
        val documentId = "$STATION_ID#$STATION_ID"
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } throws Throwable()
        coEvery {
            mockRemoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns RemoteDatabaseFareListWrapper(fareList = emptyList())

        // WHEN
        val station = stubStation()
        runBlocking { remoteDataSource.getRailFares(origin = station, destination = station) }

        // THEN
        coVerify {
            mockRemoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        }
    }

    @Test
    fun `when remote database returns rail fares getRailFares should return Success`() {
        // GIVEN
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } throws Throwable()
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
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
    }

    @Test
    fun `when service returns success getRailFares should save data to remote database`() {
        // GIVEN
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
    fun `when service returns empty list getRailFares should return InvalidQueryError`() {
        // GIVEN
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
    fun `when service returns error getRailFares should log error`() {
        // GIVEN
        val body = "".toResponseBody()
        val response = Response.error<List<RailFareResponse>>(404, body)
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns response

        // WHEN
        val station = stubStation()
        runBlocking { remoteDataSource.getRailFares(origin = station, destination = station) }

        // THEN
        verify { mockLogger.debug(message = any()) }
    }

    @Test
    fun `when service returns error getRailFares should call remote database`() {
        // GIVEN
        val body = "".toResponseBody()
        val response = Response.error<List<RailFareResponse>>(404, body)
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns response

        // WHEN
        val station = stubStation()
        runBlocking { remoteDataSource.getRailFares(origin = station, destination = station) }

        // THEN
        coVerify {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        }
    }

    @Test
    fun `when remote database returns error getRailFares should return GenericError`() {
        // GIVEN
        val body = "".toResponseBody()
        coEvery {
            mockService.getRailFares(originId = STATION_ID, destinationId = STATION_ID)
        } returns Response.error(600, body)
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null

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
