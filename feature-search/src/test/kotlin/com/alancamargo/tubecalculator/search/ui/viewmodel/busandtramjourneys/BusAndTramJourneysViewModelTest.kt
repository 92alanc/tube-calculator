package com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys

import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BusAndTramJourneysViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()
    private val viewModel = BusAndTramJourneysViewModel(dispatcher)

    private val collector = ViewModelFlowCollector(
        viewModel.state,
        viewModel.state,
        dispatcher
    )

    @Test
    fun `increaseBusAndTramJourneyCount should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            val expectedCount = 1
            viewModel.increaseBusAndTramJourneyCount()

            // THEN
            val expected = BusAndTramJourneysViewState(busAndTramJourneyCount = expectedCount)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with positive count decreaseBusAndTramJourneyCount should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            repeat(times = 2) {
                viewModel.increaseBusAndTramJourneyCount()
            }

            // WHEN
            val expectedCount = 1
            viewModel.decreaseBusAndTramJourneyCount()

            // THEN
            val expected = BusAndTramJourneysViewState(busAndTramJourneyCount = expectedCount)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with negative count decreaseBusAndTramJourneyCount should not update state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.decreaseBusAndTramJourneyCount()

            // THEN
            val expected = listOf(BusAndTramJourneysViewState())
            assertThat(states).isEqualTo(expected)
        }
    }
}
