package com.alancamargo.tubecalculator.home.ui.viewmodel

import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.core.test.viewmodel.ViewModelFlowCollector
import com.alancamargo.tubecalculator.home.data.analytics.HomeAnalytics
import com.alancamargo.tubecalculator.home.domain.usecase.DisableFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.IsFirstAccessUseCase
import com.alancamargo.tubecalculator.home.testtools.stubBusAndTramJourney
import com.alancamargo.tubecalculator.home.testtools.stubRailJourney
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val mockIsFirstAccessUseCase = mockk<IsFirstAccessUseCase>()
    private val mockDisableFirstAccessUseCase = mockk<DisableFirstAccessUseCase>(relaxed = true)
    private val mockAnalytics = mockk<HomeAnalytics>(relaxed = true)
    private val appVersionName = "2023.1.0"
    private val uiDelay = 0L
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = HomeViewModel(
        isFirstAccessUseCase = mockIsFirstAccessUseCase,
        disableFirstAccessUseCase = mockDisableFirstAccessUseCase,
        analytics = mockAnalytics,
        appVersionName = appVersionName,
        uiDelay = uiDelay,
        dispatcher = dispatcher
    )

    private val collector = ViewModelFlowCollector(
        viewModel.state,
        viewModel.action,
        dispatcher = dispatcher
    )

    @Test
    fun `on first launch onCreate should track screen view event`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = true)

        // THEN
        verify { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `on first launch and on first access onCreate should send ShowFirstAccessDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every { mockIsFirstAccessUseCase() } returns true

            // WHEN
            viewModel.onCreate(isFirstLaunch = true)

            // THEN
            assertThat(actions).contains(HomeViewAction.ShowFirstAccessDialogue)
        }
    }

    @Test
    fun `on first launch and not on first access onStart should not send ShowFirstAccessDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every { mockIsFirstAccessUseCase() } returns false

            // WHEN
            viewModel.onCreate(isFirstLaunch = true)

            // THEN
            assertThat(actions).doesNotContain(HomeViewAction.ShowFirstAccessDialogue)
        }
    }

    @Test
    fun `when not on first launch onCreate should not track screen view event`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = false)

        // THEN
        verify(exactly = 0) { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `when not on first launch onCreate should not check for first access`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = false)

        // THEN
        verify(exactly = 0) { mockIsFirstAccessUseCase() }
    }

    @Test
    fun `onFirstAccessGoToSettingsClicked should disable first access`() {
        // WHEN
        viewModel.onFirstAccessGoToSettingsClicked()

        // THEN
        verify { mockDisableFirstAccessUseCase() }
    }

    @Test
    fun `onFirstAccessGoToSettingsClicked should send NavigateToSettings action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onFirstAccessGoToSettingsClicked()

            // THEN
            assertThat(actions).contains(HomeViewAction.NavigateToSettings)
        }
    }

    @Test
    fun `onFirstAccessNotNowClicked should disable first access`() {
        // WHEN
        viewModel.onFirstAccessNotNowClicked()

        // THEN
        verify { mockDisableFirstAccessUseCase() }
    }

    @Test
    fun `onSettingsClicked should track button click event`() {
        // WHEN
        viewModel.onSettingsClicked()

        // THEN
        verify { mockAnalytics.trackSettingsClicked() }
    }

    @Test
    fun `onSettingsClicked should send NavigateToSettings action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onSettingsClicked()

            // THEN
            assertThat(actions).contains(HomeViewAction.NavigateToSettings)
        }
    }

    @Test
    fun `onPrivacyPolicyClicked should send ShowPrivacyPolicyDialogue action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onPrivacyPolicyClicked()

            // THEN
            assertThat(actions).contains(HomeViewAction.ShowPrivacyPolicyDialogue)
        }
    }

    @Test
    fun `onAppInfoClicked should track button click event`() {
        // WHEN
        viewModel.onAppInfoClicked()

        // THEN
        verify { mockAnalytics.trackAppInfoClicked() }
    }

    @Test
    fun `onAppInfoClicked should send ShowAppInfo action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onAppInfoClicked()

            // THEN
            assertThat(actions).contains(HomeViewAction.ShowAppInfo(appVersionName))
        }
    }

    @Test
    fun `onJourneyReceived should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            val journey = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(journey)

            // THEN
            val expected = HomeViewState(
                journeys = listOf(journey),
                showAddButton = true,
                showCalculateButton = true
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with rail and bus and tram journeys added onJourneyReceived should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            val rail = stubRailJourney()
            val busAndTram = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(rail)
            viewModel.onJourneyReceived(busAndTram)

            // THEN
            val expected = HomeViewState(
                journeys = listOf(rail, busAndTram),
                showAddButton = false,
                showCalculateButton = true
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onJourneyRemoved should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            val journey = stubRailJourney()
            viewModel.onJourneyReceived(journey)

            // WHEN
            viewModel.onJourneyRemoved(journey)

            // THEN
            val expected = HomeViewState(
                journeys = emptyList(),
                showAddButton = true,
                showCalculateButton = false
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onCalculateClicked should track button click event`() {
        // GIVEN
        val journey = stubRailJourney()
        viewModel.onJourneyReceived(journey)

        // WHEN
        viewModel.onCalculateClicked()

        // THEN
        verify { mockAnalytics.trackCalculateClicked(journeys = listOf(journey)) }
    }

    @Test
    fun `onCalculateClicked should send NavigateToSearch action`() {
        collector.test { _, actions ->
            // GIVEN
            val journey = stubBusAndTramJourney()
            viewModel.onJourneyReceived(journey)

            // WHEN
            viewModel.onCalculateClicked()

            // THEN
            val journeys = listOf(journey)
            val expected = HomeViewAction.NavigateToSearch(journeys)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `with no journeys onAddClicked should send ExpandAddButton with correct options`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onAddClicked()

            // THEN
            val expected = HomeViewAction.ExpandAddButton(
                options = listOf(JourneyType.RAIL, JourneyType.BUS_AND_TRAM)
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `with rail journey onAddClicked should send ExpandAddButton with correct options`() {
        collector.test { _, actions ->
            // GIVEN
            viewModel.onJourneyReceived(journey = stubRailJourney())

            // WHEN
            viewModel.onAddClicked()

            // THEN
            val expected = HomeViewAction.ExpandAddButton(
                options = listOf(JourneyType.BUS_AND_TRAM)
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `with bus and tram journey onAddClicked should send ExpandAddButton with correct options`() {
        collector.test { _, actions ->
            // GIVEN
            viewModel.onJourneyReceived(journey = stubBusAndTramJourney())

            // WHEN
            viewModel.onAddClicked()

            // THEN
            val expected = HomeViewAction.ExpandAddButton(options = listOf(JourneyType.RAIL))
            assertThat(actions).contains(expected)
        }
    }
}
