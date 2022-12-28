package com.alancamargo.tubecalculator.fares.data.repository

import app.cash.turbine.test
import com.alancamargo.tubecalculator.fares.data.remote.FaresRemoteDataSource
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FaresRepositoryImplTest {

    private val mockRemoteDataSource = mockk<FaresRemoteDataSource>()
    private val repository = FaresRepositoryImpl(mockRemoteDataSource)

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
}
