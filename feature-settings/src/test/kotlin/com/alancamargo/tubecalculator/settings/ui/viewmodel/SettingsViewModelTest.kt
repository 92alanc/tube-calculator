package com.alancamargo.tubecalculator.settings.ui.viewmodel

import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.settings.domain.usecase.IsCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.SetCrashLoggingEnabledUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val mockIsCrashLoggingEnabledUseCase = mockk<IsCrashLoggingEnabledUseCase>()
    private val mockSetCrashLoggingEnabledUseCase = mockk<SetCrashLoggingEnabledUseCase>(
        relaxed = true
    )
    private val viewModel = SettingsViewModel(
        mockIsCrashLoggingEnabledUseCase,
        mockSetCrashLoggingEnabledUseCase
    )

    private val dispatcher = TestCoroutineDispatcher()
    private val collector = ViewModelFlowCollector(
        viewModel.state,
        viewModel.state,
        dispatcher
    )

    @Test
    fun `onCreate should update state with crash logging setting`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockIsCrashLoggingEnabledUseCase() } returns true

            // WHEN
            viewModel.onCreate()

            // THEN
            val expected = SettingsViewState(isCrashLoggingEnabled = true)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onCrashLoggingToggled should update state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onCrashLoggingToggled()

            // THEN
            val expected = SettingsViewState(isCrashLoggingEnabled = true)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onCrashLoggingToggled should update setting`() {
        // WHEN
        viewModel.onCrashLoggingToggled()

        // THEN
        verify { mockSetCrashLoggingEnabledUseCase(isEnabled = true) }
    }
}
