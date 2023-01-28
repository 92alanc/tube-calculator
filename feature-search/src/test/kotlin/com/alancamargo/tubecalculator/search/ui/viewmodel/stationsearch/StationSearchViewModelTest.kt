package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.usecase.GetAllStationsUseCase
import com.alancamargo.tubecalculator.search.domain.usecase.GetMinQueryLengthUseCase
import com.alancamargo.tubecalculator.search.testtools.MIN_QUERY_LENGTH
import com.alancamargo.tubecalculator.search.testtools.stubSuccessfulStationListResultFlow
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StationSearchViewModelTest {

    private val mockGetAllStationsUseCase = mockk<GetAllStationsUseCase>()
    private val mockGetMinQueryLengthUseCase = mockk<GetMinQueryLengthUseCase>()
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = StationSearchViewModel(
        mockGetAllStationsUseCase,
        mockGetMinQueryLengthUseCase,
        mockLogger,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.state,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    @Before
    fun setUp() {
        every { mockGetMinQueryLengthUseCase() } returns MIN_QUERY_LENGTH
    }

    @Test
    fun `when use case returns Success onCreate should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockGetAllStationsUseCase() } returns stubSuccessfulStationListResultFlow()

            // WHEN
            viewModel.onCreate()

            // THEN
            val stations = stubUiStationList()
            val expected = StationSearchViewState(
                stations = stations,
                minQueryLength = MIN_QUERY_LENGTH
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when use case returns GenericError onCreate should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every { mockGetAllStationsUseCase() } returns flowOf(StationListResult.GenericError)

            // WHEN
            viewModel.onCreate()

            // THEN
            val expected = StationSearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case throws exception onCreate should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every { mockGetAllStationsUseCase() } returns flow { throw Throwable() }

            // WHEN
            viewModel.onCreate()

            // THEN
            val expected = StationSearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case throws exception onCreate should log exception`() {
        collector.test { _, _ ->
            // GIVEN
            val exception = Throwable()
            every { mockGetAllStationsUseCase() } returns flow { throw exception }

            // WHEN
            viewModel.onCreate()

            // THEN
            verify { mockLogger.error(exception) }
        }
    }
}
