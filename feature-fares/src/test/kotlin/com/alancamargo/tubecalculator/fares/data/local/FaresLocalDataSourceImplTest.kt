package com.alancamargo.tubecalculator.fares.data.local

import com.alancamargo.tubecalculator.fares.data.database.RailFaresDao
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import com.alancamargo.tubecalculator.fares.testtools.stubDbRailFare
import com.alancamargo.tubecalculator.fares.testtools.stubRailFare
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FaresLocalDataSourceImplTest {

    private val mockDao = mockk<RailFaresDao>(relaxed = true)
    private val localDataSource = FaresLocalDataSourceImpl(mockDao)

    @Test
    fun `getRailFares should return fares from database`() {
        // GIVEN
        val station = stubStation()
        val dbFares = stubDbRailFare()
        coEvery {
            mockDao.getRailFares(originId = station.id, destinationId = station.id)
        } returns dbFares

        // WHEN
        val actual = runBlocking {
            localDataSource.getRailFares(origin = station, destination = station)
        }

        // THEN
        val expected = RailFaresResult.Success(listOf(stubRailFare()))
        assertThat(actual).isEqualTo(expected)
    }

    @Test(expected = Throwable::class)
    fun `when database returns null getRailFares should throw exception`() {
        // GIVEN
        val station = stubStation()
        coEvery {
            mockDao.getRailFares(originId = station.id, destinationId = station.id)
        } returns null

        // THEN
        runBlocking { localDataSource.getRailFares(origin = station, destination = station) }
    }

    @Test
    fun `when fares are present on database saveFares should update value`() {
        // GIVEN
        val station = stubStation()
        coEvery { mockDao.getRailFareCount(id = any()) } returns 1

        // WHEN
        val fares = listOf(stubRailFare())
        runBlocking {
            localDataSource.saveRailFares(origin = station, destination = station, railFares = fares)
        }

        // THEN
        coVerify { mockDao.updateRailFares(fare = any()) }
        coVerify(exactly = 0) { mockDao.insertRailFares(fare = any()) }
    }

    @Test
    fun `when fares are not present on database saveFares should insert value`() {
        // GIVEN
        val station = stubStation()
        coEvery { mockDao.getRailFareCount(id = any()) } returns 0

        // WHEN
        val fares = listOf(stubRailFare())
        runBlocking {
            localDataSource.saveRailFares(origin = station, destination = station, railFares = fares)
        }

        // THEN
        coVerify { mockDao.insertRailFares(fare = any()) }
        coVerify(exactly = 0) { mockDao.updateRailFares(fare = any()) }
    }

    @Test
    fun `clearCache should delete all fares from database`() {
        // WHEN
        runBlocking { localDataSource.clearCache() }

        // THEN
        coVerify { mockDao.deleteAllRailFares() }
    }
}
