package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class GetSearchTriggerDelayUseCaseImplTest {

    private val mockRemoteConfigManager = mockk<RemoteConfigManager>()
    private val useCase = GetSearchTriggerDelayUseCaseImpl(mockRemoteConfigManager)

    @Test
    fun `invoke should get search trigger delay from remote config`() {
        // GIVEN
        val expected = 1234L
        every {
            mockRemoteConfigManager.getLong(key = "search_trigger_delay_millis")
        } returns expected

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isEqualTo(expected)
    }
}
