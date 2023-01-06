package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.usecase.SearchStationUseCase
import com.alancamargo.tubecalculator.search.testtools.SEARCH_QUERY
import com.alancamargo.tubecalculator.search.testtools.stubSuccessfulSearchFlow
import com.alancamargo.tubecalculator.search.testtools.stubUiStation
import com.alancamargo.tubecalculator.search.testtools.stubUiStationList
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class StationSearchViewModelTest {

    private val mockSearchStationUseCase = mockk<SearchStationUseCase>()
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val dispatcher = TestCoroutineDispatcher()
    private val viewModel = StationSearchViewModel(
        mockSearchStationUseCase,
        mockLogger,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.state,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    @Test
    fun `when use case returns Success searchStation should set correct states`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns stubSuccessfulSearchFlow()

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val stations = stubUiStationList()
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState(
                    isLoading = true,
                    searchResults = stations
                ),
                StationSearchViewState(searchResults = stations)
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns Success searchStation should not log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns stubSuccessfulSearchFlow()

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            verify(exactly = 0) { mockLogger.debug(message = any()) }
        }
    }

    @Test
    fun `when use case returns Empty searchStation should set correct states`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns flowOf(StationListResult.Empty)

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState(
                    isLoading = true,
                    showEmptyState = true
                ),
                StationSearchViewState(showEmptyState = true)
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns Empty searchStation should not log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns flowOf(StationListResult.Empty)

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            verify(exactly = 0) { mockLogger.debug(message = any()) }
        }
    }

    @Test
    fun `when use case returns NetworkError searchStation should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.NetworkError)

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.NETWORK)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns NetworkError searchStation should not log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.NetworkError)

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            verify(exactly = 0) { mockLogger.debug(message = any()) }
        }
    }

    @Test
    fun `when use case returns ServerError searchStation should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.ServerError)

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.SERVER)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns ServerError searchStation should log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.ServerError)

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val message = "Query: $SEARCH_QUERY. Result: ${StationListResult.ServerError}"
            verify { mockLogger.debug(message) }
        }
    }

    @Test
    fun `when use case returns GenericError searchStation should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.GenericError)

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns GenericError searchStation should log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.GenericError)

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val message = "Query: $SEARCH_QUERY. Result: ${StationListResult.GenericError}"
            verify { mockLogger.debug(message) }
        }
    }

    @Test
    fun `when use case throws IOException searchStation should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flow { throw IOException() }

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.NETWORK)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case throws generic exception searchStation should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flow { throw Throwable() }

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case throws exception searchStation should log exception`() {
        collector.test { _, _ ->
            // GIVEN
            val exception = Throwable()
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flow { throw exception }

            // WHEN
            viewModel.searchStation(SEARCH_QUERY)

            // THEN
            verify { mockLogger.error(exception) }
        }
    }

    @Test
    fun `onStationSelected should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            val station = stubUiStation(name = "Tottenham Court Road")
            viewModel.onStationSelected(station)

            // THEN
            val expected = StationSearchViewState(searchResults = listOf(station))
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when query is empty onQueryChanged should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onQueryChanged(query = "")

            // THEN
            val expected = StationSearchViewState()
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when query is not empty onQueryChanged should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expected = StationSearchViewState(isSearchButtonEnabled = true)
            assertThat(states).contains(expected)
        }
    }
}
