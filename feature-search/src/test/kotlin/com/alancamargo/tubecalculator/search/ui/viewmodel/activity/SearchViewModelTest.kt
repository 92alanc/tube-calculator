package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.core.test.viewmodel.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val mockAnalytics = mockk<SearchAnalytics>(relaxed = true)
    private val uiDelay = 0L
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = SearchViewModel(
        mockAnalytics,
        uiDelay,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.action,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    @Test
    fun `on first launch onCreate should send AttachFragments action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onCreate(isFirstLaunch = true)

            // THEN
            val expected = SearchViewAction.AttachFragments
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `on first launch onCreate should track screen view event`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = true)

        // THEN
        verify { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `when not on first launch onCreate should not send AttachFragments action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onCreate(isFirstLaunch = false)

            // THEN
            val expected = SearchViewAction.AttachFragments
            assertThat(actions).doesNotContain(expected)
        }
    }

    @Test
    fun `when not on first launch onCreate should not track screen view event`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = false)

        // THEN
        verify(exactly = 0) { mockAnalytics.trackScreenViewed() }
    }
}
