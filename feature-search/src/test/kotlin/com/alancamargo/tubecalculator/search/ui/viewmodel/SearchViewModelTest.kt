package com.alancamargo.tubecalculator.search.ui.viewmodel

import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.domain.usecase.SearchStationUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val mockSearchStationUseCase = mockk<SearchStationUseCase>()
    private val dispatcher = TestCoroutineDispatcher()
    private val viewModel = SearchViewModel(
        mockSearchStationUseCase,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.state,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    // TODO

    @Test
    fun `with negative count onUpdateBusAndTramJourneyCount should not update state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onUpdateBusAndTramJourneyCount(count = -1)

            // THEN
            val expected = listOf(SearchViewState())
            assertThat(states).isEqualTo(expected)
        }
    }

    @Test
    fun `with positive count onUpdateBusAndTramJourneyCount should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            val expectedCount = 1
            viewModel.onUpdateBusAndTramJourneyCount(expectedCount)

            // THEN
            val expected = SearchViewState(busAndTramJourneyCount = expectedCount)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onAppInfoClicked should send ShowAppInfo action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onAppInfoClicked()

            // THEN
            assertThat(actions).contains(SearchViewAction.ShowAppInfo)
        }
    }
}
