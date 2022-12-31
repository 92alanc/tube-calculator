package com.alancamargo.tubecalculator.fares.data.local

import com.alancamargo.tubecalculator.fares.data.database.FaresDao
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.testtools.stubDbFareListRoot
import com.alancamargo.tubecalculator.fares.testtools.stubFareListRoot
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FaresLocalDataSourceImplTest {

    private val mockDao = mockk<FaresDao>(relaxed = true)
    private val localDataSource = FaresLocalDataSourceImpl(mockDao)

    @Test
    fun `getFares should return fares from database`() {
        // GIVEN
        val station = stubStation()
        val dbFares = stubDbFareListRoot()
        coEvery {
            mockDao.getFares(originId = station.id, destinationId = station.id)
        } returns dbFares

        // WHEN
        val actual = runBlocking {
            localDataSource.getFares(origin = station, destination = station)
        }

        // THEN
        val expected = FareListResult.Success(listOf(stubFareListRoot()))
        assertThat(actual).isEqualTo(expected)
    }

    @Test(expected = Throwable::class)
    fun `when database returns null getFares should throw exception`() {
        // GIVEN
        val station = stubStation()
        coEvery {
            mockDao.getFares(originId = station.id, destinationId = station.id)
        } returns null

        // THEN
        runBlocking { localDataSource.getFares(origin = station, destination = station) }
    }

    @Test
    fun `when fares are present on database saveFares should update value`() {
        // GIVEN
        val expected = stubDbFareListRoot()
        val station = stubStation()
        coEvery { mockDao.getFare(id = any()) } returns expected

        // WHEN
        val fares = listOf(stubFareListRoot())
        runBlocking {
            localDataSource.saveFares(origin = station, destination = station, fares = fares)
        }

        // THEN
        coVerify { mockDao.updateFares(fare = any()) }
        coVerify(exactly = 0) { mockDao.insertFares(fare = any()) }
    }

    @Test
    fun `when fares are not present on database saveFares should insert value`() {
        // GIVEN
        val station = stubStation()
        coEvery { mockDao.getFare(id = any()) } returns null

        // WHEN
        val fares = listOf(stubFareListRoot())
        runBlocking {
            localDataSource.saveFares(origin = station, destination = station, fares = fares)
        }

        // THEN
        coVerify { mockDao.insertFares(fare = any()) }
        coVerify(exactly = 0) { mockDao.updateFares(fare = any()) }
    }
}
