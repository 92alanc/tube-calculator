package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class GetMinQueryLengthUseCaseImplTest {

    private val mockRemoteConfigManager = mockk<RemoteConfigManager>()
    private val useCase = GetMinQueryLengthUseCaseImpl(mockRemoteConfigManager)

    @Test
    fun `invoke should get minimum query length from remote config`() {
        // GIVEN
        val expected = 123
        every { mockRemoteConfigManager.getInt(key = "min_query_length") } returns expected

        // WHEN
        val actual = useCase()

        // THEN
        assertThat(actual).isEqualTo(expected)
    }
}
