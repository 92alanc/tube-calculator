package com.alancamargo.tubecalculator.home.ui.viewmodel

import com.alancamargo.tubecalculator.core.test.viewmodel.ViewModelFlowCollector
import com.alancamargo.tubecalculator.home.data.analytics.HomeAnalytics
import com.alancamargo.tubecalculator.home.domain.usecase.DisableDeleteJourneyTutorialUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.DisableFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.IsFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.ShouldShowDeleteJourneyTutorialUseCase
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
    private val mockShouldShowDeleteJourneyTutorialUseCase = mockk<ShouldShowDeleteJourneyTutorialUseCase>()
    private val mockDisableDeleteJourneyTutorialUseCase = mockk<DisableDeleteJourneyTutorialUseCase>(
        relaxed = true
    )
    private val mockAnalytics = mockk<HomeAnalytics>(relaxed = true)
    private val appVersionName = "2023.1.0"
    private val uiDelay = 0L
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = HomeViewModel(
        isFirstAccessUseCase = mockIsFirstAccessUseCase,
        disableFirstAccessUseCase = mockDisableFirstAccessUseCase,
        shouldShowDeleteJourneyTutorialUseCase = mockShouldShowDeleteJourneyTutorialUseCase,
        disableDeleteJourneyTutorialUseCase = mockDisableDeleteJourneyTutorialUseCase,
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
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns false
            val journey = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(journey)

            // THEN
            val expected = HomeViewState(
                journeys = listOf(journey),
                showAddButton = true,
                showCalculateButton = true,
                showEmptyState = false
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with rail and bus and tram journeys added onJourneyReceived should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns false
            val rail = stubRailJourney()
            val busAndTram = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(rail)
            viewModel.onJourneyReceived(busAndTram)

            // THEN
            val expected = HomeViewState(
                journeys = listOf(rail, busAndTram),
                showAddButton = false,
                showCalculateButton = true,
                showEmptyState = false
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with existing rail journey onJourneyReceived should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns false
            val rail = stubRailJourney()

            // WHEN
            viewModel.onJourneyReceived(rail)
            viewModel.onJourneyReceived(rail)

            // THEN
            val expected = HomeViewState(
                journeys = listOf(rail),
                showAddButton = true,
                showCalculateButton = true,
                showEmptyState = false
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with existing bus and tram journey onJourneyReceived should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns false
            val busAndTram = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(busAndTram)
            viewModel.onJourneyReceived(busAndTram)

            // THEN
            val expected = HomeViewState(
                journeys = listOf(busAndTram),
                showAddButton = true,
                showCalculateButton = true,
                showEmptyState = false
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when use case returns true onJourneyReceived should send ShowDeleteJourneyTutorial action`() {
        collector.test { _, actions ->
            // GIVEN
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns true
            val journey = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(journey)

            // THEN
            val illustrationAssetName = "delete_journey.gif"
            val expected = HomeViewAction.ShowDeleteJourneyTutorial(illustrationAssetName)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case returns true onJourneyReceived should disable delete journey tutorial`() {
        // GIVEN
        every { mockShouldShowDeleteJourneyTutorialUseCase() } returns true
        val journey = stubBusAndTramJourney()

        // WHEN
        viewModel.onJourneyReceived(journey)

        // THEN
        verify { mockDisableDeleteJourneyTutorialUseCase() }
    }

    @Test
    fun `onJourneyRemoved should track event`() {
        // GIVEN
        val journey = stubRailJourney()
        viewModel.onJourneyReceived(journey)

        // WHEN
        viewModel.onJourneyRemoved(journeyPosition = 0)

        // THEN
        verify { mockAnalytics.trackJourneyRemoved() }
    }

    @Test
    fun `onJourneyRemoved should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            val journey = stubRailJourney()
            viewModel.onJourneyReceived(journey)

            // WHEN
            viewModel.onJourneyRemoved(journeyPosition = 0)

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
            val expected = HomeViewAction.NavigateToFares(journeys)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `with no journeys onAddClicked should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onAddClicked()

            // THEN
            val expected = HomeViewState(
                isAddButtonExpanded = true,
                showAddBusAndTramJourneyButton = true,
                showAddRailJourneyButton = true,
                showEmptyState = false
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with rail journey onAddClicked should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            val journey = stubRailJourney()
            viewModel.onJourneyReceived(journey)

            // WHEN
            viewModel.onAddClicked()

            // THEN
            val expected = HomeViewState(
                journeys = listOf(journey),
                showCalculateButton = true,
                isAddButtonExpanded = true,
                showAddBusAndTramJourneyButton = true,
                showAddRailJourneyButton = false,
                showEmptyState = false
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with bus and tram journey onAddClicked should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            val journey = stubBusAndTramJourney()
            viewModel.onJourneyReceived(journey)

            // WHEN
            viewModel.onAddClicked()

            // THEN
            val expected = HomeViewState(
                journeys = listOf(journey),
                showCalculateButton = true,
                isAddButtonExpanded = true,
                showAddBusAndTramJourneyButton = false,
                showAddRailJourneyButton = true,
                showEmptyState = false
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when button is expanded onAddedClicked should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            viewModel.onAddClicked()

            // WHEN
            viewModel.onAddClicked()

            // THEN
            val expected = HomeViewState(isAddButtonExpanded = false)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onJourneyClicked should track event`() {
        // WHEN
        viewModel.onJourneyClicked(journey = stubRailJourney())

        // THEN
        verify { mockAnalytics.trackJourneyClicked() }
    }

    @Test
    fun `onJourneyClicked should send EditJourney event`() {
        collector.test { _, actions ->
            // GIVEN
            val journey = stubRailJourney()

            // WHEN
            viewModel.onJourneyClicked(journey)
            
            // THEN
            val expected = HomeViewAction.EditJourney(journey)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `onAddRailJourneyClicked should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onAddRailJourneyClicked()

            // THEN
            val expected = HomeViewState(isAddButtonExpanded = false)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onAddRailJourneyClicked should send AddRailJourney action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onAddRailJourneyClicked()

            // THEN
            val expected = HomeViewAction.AddRailJourney
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `onAddBusAndTramJourneyClicked should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onAddBusAndTramJourneyClicked()

            // THEN
            val expected = HomeViewState(isAddButtonExpanded = false)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onAddBusAndTramJourneyClicked should send AddBusAndTramJourney action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onAddBusAndTramJourneyClicked()

            // THEN
            val expected = HomeViewAction.AddBusAndTramJourney
            assertThat(actions).contains(expected)
        }
    }
}
