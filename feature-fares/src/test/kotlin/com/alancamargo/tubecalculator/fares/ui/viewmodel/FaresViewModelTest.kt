package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.common.ui.mapping.toUi
import com.alancamargo.tubecalculator.core.design.text.BulletListFormatter
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalytics
import com.alancamargo.tubecalculator.fares.data.work.RailFaresCacheWorkScheduler
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateBusAndTramFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateCheapestTotalFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.GetRailFaresUseCase
import com.alancamargo.tubecalculator.fares.testtools.*
import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError
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
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class FaresViewModelTest {

    private val mockGetRailFaresUseCase = mockk<GetRailFaresUseCase>()
    private val mockCalculateBusAndTramFareUseCase = mockk<CalculateBusAndTramFareUseCase>()
    private val mockCalculateCheapestTotalFareUseCase = mockk<CalculateCheapestTotalFareUseCase>()
    private val mockBulletListFormatter = mockk<BulletListFormatter>()
    private val mockRailFaresCacheWorkScheduler = mockk<RailFaresCacheWorkScheduler>(relaxed = true)
    private val mockAnalytics = mockk<FaresAnalytics>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = FaresViewModel(
        mockGetRailFaresUseCase,
        mockCalculateBusAndTramFareUseCase,
        mockCalculateCheapestTotalFareUseCase,
        mockBulletListFormatter,
        mockRailFaresCacheWorkScheduler,
        mockAnalytics,
        mockLogger,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        viewModel.state,
        viewModel.action,
        dispatcher
    )

    private val station = stubStation()
    private val uiStation = station.toUi()

    @Before
    fun setUp() {
        every {
            mockCalculateBusAndTramFareUseCase(BUS_AND_TRAM_JOURNEY_COUNT)
        } returns Fare.BusAndTramFare(BUS_AND_TRAM_FARE)

        every {
            mockCalculateCheapestTotalFareUseCase(fares = any())
        } returns CHEAPEST_TOTAL_FARE
    }

    @Test
    fun `onCreate should track screen view event`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = any(), destination = any())
        } returns flowOf(RailFaresResult.NetworkError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `onCreate should schedule fares cache background work`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = any(), destination = any())
        } returns flowOf(RailFaresResult.NetworkError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // WHEN
        verify { mockRailFaresCacheWorkScheduler.scheduleRailFaresCacheBackgroundWork() }
    }

    @Test
    fun `when use case returns Success onCreate should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flowOf(RailFaresResult.Success(listOf(stubRailFare())))

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val fares = listOf(stubRailFare(), Fare.BusAndTramFare(BUS_AND_TRAM_FARE))
            val expected = listOf(
                FaresViewState(isLoading = true),
                FaresViewState(
                    isLoading = false,
                    fares = fares,
                    cheapestTotalFare = CHEAPEST_TOTAL_FARE
                )
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns Success onCreate should not log result`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flowOf(RailFaresResult.Success(listOf(stubRailFare())))

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `with only bus and tram fare onCreate should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onCreate(
                origin = null,
                destination = null,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewState(
                fares = listOf(Fare.BusAndTramFare(BUS_AND_TRAM_FARE)),
                cheapestTotalFare = CHEAPEST_TOTAL_FARE
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with only bus and tram fare onCreate should not log result`() {
        // WHEN
        viewModel.onCreate(
            origin = null,
            destination = null,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `when use case returns InvalidQueryError onCreate should set correct state and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flowOf(RailFaresResult.InvalidQueryError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = listOf(
                FaresViewState(isLoading = true),
                FaresViewState(
                    isLoading = false,
                    fares = listOf(Fare.BusAndTramFare(BUS_AND_TRAM_FARE)),
                    cheapestTotalFare = CHEAPEST_TOTAL_FARE
                )
            )
            assertThat(states).containsAtLeastElementsIn(expected)
            assertThat(actions).contains(
                FaresViewAction.ShowErrorDialogue(UiFaresError.INVALID_QUERY)
            )
        }
    }

    @Test
    fun `when use case returns NetworkError onCreate should set correct state and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flowOf(RailFaresResult.NetworkError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = listOf(
                FaresViewState(isLoading = true),
                FaresViewState(
                    isLoading = false,
                    fares = listOf(Fare.BusAndTramFare(BUS_AND_TRAM_FARE)),
                    cheapestTotalFare = CHEAPEST_TOTAL_FARE
                )
            )
            assertThat(states).containsAtLeastElementsIn(expected)
            assertThat(actions).contains(FaresViewAction.ShowErrorDialogue(UiFaresError.NETWORK))
        }
    }

    @Test
    fun `when use case returns NetworkError onCreate should not log result`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flowOf(RailFaresResult.NetworkError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `when use case returns ServerError onCreate should set correct state and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flowOf(RailFaresResult.ServerError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = listOf(
                FaresViewState(isLoading = true),
                FaresViewState(
                    isLoading = false,
                    fares = listOf(Fare.BusAndTramFare(BUS_AND_TRAM_FARE)),
                    cheapestTotalFare = CHEAPEST_TOTAL_FARE
                )
            )
            assertThat(states).containsAtLeastElementsIn(expected)
            assertThat(actions).contains(FaresViewAction.ShowErrorDialogue(UiFaresError.SERVER))
        }
    }

    @Test
    fun `when use case returns ServerError onCreate should log result`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flowOf(RailFaresResult.ServerError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        val message =
            "Origin: ${uiStation.name}. Destination: ${uiStation.name}. Result: ${RailFaresResult.ServerError}"
        verify { mockLogger.debug(message) }
    }

    @Test
    fun `when use case returns GenericError onCreate should set correct state and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flowOf(RailFaresResult.GenericError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = listOf(
                FaresViewState(isLoading = true),
                FaresViewState(
                    isLoading = false,
                    fares = listOf(Fare.BusAndTramFare(BUS_AND_TRAM_FARE)),
                    cheapestTotalFare = CHEAPEST_TOTAL_FARE
                )
            )
            assertThat(states).containsAtLeastElementsIn(expected)
            assertThat(actions).contains(FaresViewAction.ShowErrorDialogue(UiFaresError.GENERIC))
        }
    }

    @Test
    fun `when use case returns GenericError onCreate should log result`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flowOf(RailFaresResult.GenericError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        val message =
            "Origin: ${uiStation.name}. Destination: ${uiStation.name}. Result: ${RailFaresResult.GenericError}"
        verify { mockLogger.debug(message) }
    }

    @Test
    fun `when use case throws IOException onCreate should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flow { throw IOException() }

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.NETWORK)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case throws generic exception onCreate should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flow { throw Throwable() }

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.GENERIC)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case throws exception onCreate should log exception`() {
        // GIVEN
        val exception = Throwable()
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flow { throw exception }

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify { mockLogger.error(exception) }
    }

    @Test
    fun `onDismissErrorDialogue should send Finish action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onDismissErrorDialogue()

            // THEN
            assertThat(actions).contains(FaresViewAction.Finish)
        }
    }

    @Test
    fun `onNewSearchClicked should track button click event`() {
        // WHEN
        viewModel.onNewSearchClicked()

        // THEN
        verify { mockAnalytics.trackNewSearchClicked() }
    }

    @Test
    fun `onNewSearchClicked should send NavigateToSearch action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onNewSearchClicked()

            // THEN
            assertThat(actions).contains(FaresViewAction.NavigateToSearch)
        }
    }

    @Test
    fun `onMessagesButtonClicked should track button click event`() {
        // GIVEN
        every { mockBulletListFormatter.getBulletList(strings = any()) } returns ""

        // WHEN
        viewModel.onMessagesButtonClicked(messages = emptyList())

        // THEN
        verify { mockAnalytics.trackMessagesClicked() }
    }

    @Test
    fun `onMessagesButtonClicked should send ShowMessagesDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val messages = listOf("Message 1", "Message 2", "Message 3")
            val expected = "Bullet list"
            every { mockBulletListFormatter.getBulletList(messages) } returns expected

            // WHEN
            viewModel.onMessagesButtonClicked(messages)

            // THEN
            assertThat(actions).contains(FaresViewAction.ShowMessagesDialogue(expected))
        }
    }
}
