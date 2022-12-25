package com.alancamargo.tubecalculator.search.ui.viewmodel

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

    @Test
    fun `when use case returns Success searchOrigin should set correct states`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns stubSuccessfulSearchFlow()

            // WHEN
            viewModel.searchOrigin(SEARCH_QUERY)

            // THEN
            val stations = stubUiStationList()
            val expected = listOf(
                SearchViewState(isLoadingOriginSearchResults = true),
                SearchViewState(
                    isLoadingOriginSearchResults = true,
                    originSearchResults = stations
                ),
                SearchViewState(originSearchResults = stations)
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns Empty searchOrigin should set correct states`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns flowOf(StationListResult.Empty)

            // WHEN
            viewModel.searchOrigin(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                SearchViewState(isLoadingOriginSearchResults = true),
                SearchViewState(
                    isLoadingOriginSearchResults = true,
                    showOriginEmptyState = true
                ),
                SearchViewState(showOriginEmptyState = true)
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns NetworkError searchOrigin should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.NetworkError)

            // WHEN
            viewModel.searchOrigin(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                SearchViewState(isLoadingOriginSearchResults = true),
                SearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = SearchViewAction.ShowErrorDialogue(UiSearchError.NETWORK)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns ServerError searchOrigin should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.ServerError)

            // WHEN
            viewModel.searchOrigin(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                SearchViewState(isLoadingOriginSearchResults = true),
                SearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = SearchViewAction.ShowErrorDialogue(UiSearchError.SERVER)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns GenericError searchOrigin should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.GenericError)

            // WHEN
            viewModel.searchOrigin(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                SearchViewState(isLoadingOriginSearchResults = true),
                SearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = SearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns Success searchDestination should set correct states`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns stubSuccessfulSearchFlow()

            // WHEN
            viewModel.searchDestination(SEARCH_QUERY)

            // THEN
            val stations = stubUiStationList()
            val expected = listOf(
                SearchViewState(isLoadingDestinationSearchResults = true),
                SearchViewState(
                    isLoadingDestinationSearchResults = true,
                    destinationSearchResults = stations
                ),
                SearchViewState(destinationSearchResults = stations)
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns Empty searchDestination should set correct states`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns flowOf(StationListResult.Empty)

            // WHEN
            viewModel.searchDestination(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                SearchViewState(isLoadingDestinationSearchResults = true),
                SearchViewState(
                    isLoadingDestinationSearchResults = true,
                    showDestinationEmptyState = true
                ),
                SearchViewState(showDestinationEmptyState = true)
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns NetworkError searchDestination should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.NetworkError)

            // WHEN
            viewModel.searchDestination(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                SearchViewState(isLoadingDestinationSearchResults = true),
                SearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = SearchViewAction.ShowErrorDialogue(UiSearchError.NETWORK)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns ServerError searchDestination should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.ServerError)

            // WHEN
            viewModel.searchDestination(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                SearchViewState(isLoadingDestinationSearchResults = true),
                SearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = SearchViewAction.ShowErrorDialogue(UiSearchError.SERVER)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns GenericError searchDestination should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.GenericError)

            // WHEN
            viewModel.searchDestination(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                SearchViewState(isLoadingDestinationSearchResults = true),
                SearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = SearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expectedAction)
        }
    }

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

    @Test
    fun `onCalculateClicked should send NavigateToFares action`() {
        collector.test { _, actions ->
            // GIVEN
            val origin = stubUiStation(name = "Romford")
            viewModel.onOriginSelected(origin)

            val destination = stubUiStation(name = "Westminster")
            viewModel.onDestinationSelected(destination)

            val busAndTramJourneyCount = 3
            viewModel.onUpdateBusAndTramJourneyCount(busAndTramJourneyCount)

            // WHEN
            viewModel.onCalculateClicked()

            // THEN
            val expected = SearchViewAction.NavigateToFares(
                origin = origin,
                destination = destination,
                busAndTramJourneyCount = busAndTramJourneyCount
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when origin is null onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val destination = stubUiStation(name = "Westminster")
            viewModel.onDestinationSelected(destination)

            // WHEN
            viewModel.onCalculateClicked()

            // THEN
            val expected = SearchViewAction.ShowErrorDialogue(
                UiSearchError.MISSING_ORIGIN_OR_DESTINATION
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when destination is null onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val origin = stubUiStation(name = "Romford")
            viewModel.onOriginSelected(origin)

            // WHEN
            viewModel.onCalculateClicked()

            // THEN
            val expected = SearchViewAction.ShowErrorDialogue(
                UiSearchError.MISSING_ORIGIN_OR_DESTINATION
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when origin and destination are the same onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val origin = stubUiStation(name = "Romford")
            viewModel.onOriginSelected(origin)
            viewModel.onDestinationSelected(origin)

            // WHEN
            viewModel.onCalculateClicked()

            // THEN
            val expected = SearchViewAction.ShowErrorDialogue(
                UiSearchError.SAME_ORIGIN_AND_DESTINATION
            )
            assertThat(actions).contains(expected)
        }
    }
}
