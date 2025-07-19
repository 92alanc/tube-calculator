package com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BusAndTramJourneysViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val viewModel = BusAndTramJourneysViewModel(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `onCreate should set correct view state`() = runTest {
        // GIVEN
        val journeyCount = 2

        // WHEN
        viewModel.onCreate(journeyCount)

        // THEN
        val expected = BusAndTramJourneysViewState(journeyCount)
        viewModel.state.test {
            skipItems(count = 1)
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `increaseBusAndTramJourneyCount should set correct state`() = runTest {
        // WHEN
        val expectedCount = 1
        viewModel.increaseBusAndTramJourneyCount()

        // THEN
        val expected = BusAndTramJourneysViewState(busAndTramJourneyCount = expectedCount)
        viewModel.state.test {
            skipItems(count = 1)
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with positive count decreaseBusAndTramJourneyCount should set correct state`() = runTest {
        // GIVEN
        repeat(times = 2) {
            viewModel.increaseBusAndTramJourneyCount()
        }

        // WHEN
        val expectedCount = 1
        viewModel.decreaseBusAndTramJourneyCount()

        // THEN
        val expected = BusAndTramJourneysViewState(busAndTramJourneyCount = expectedCount)
        viewModel.state.test {
            skipItems(count = 1)
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onMoreInfoClicked should send ShowMoreInfo action`() = runTest {
        // WHEN
        viewModel.onMoreInfoClicked()

        // THEN
        val expected = BusAndTramJourneysViewAction.ShowMoreInfo
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }
}
