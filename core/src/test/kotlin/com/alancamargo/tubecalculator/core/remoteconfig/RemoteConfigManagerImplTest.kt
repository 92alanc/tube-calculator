package com.alancamargo.tubecalculator.core.remoteconfig

import com.google.common.truth.Truth.assertThat
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

private const val KEY = "test_key"

class RemoteConfigManagerImplTest {

    private val mockFirebaseRemoteConfig = mockk<FirebaseRemoteConfig>()
    private val remoteConfigManager = RemoteConfigManagerImpl(mockFirebaseRemoteConfig)

    @Test
    fun `getDouble should get double value from firebase`() {
        // GIVEN
        val expected = 1.65
        every { mockFirebaseRemoteConfig.getDouble(KEY) } returns expected

        // WHEN
        val actual = remoteConfigManager.getDouble(KEY)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }
}
