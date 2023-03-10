package com.alancamargo.tubecalculator.core.remoteconfig

import com.google.common.truth.Truth.assertThat
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Test

private const val KEY = "test_key"

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteConfigManagerImplTest {

    private val mockFirebaseRemoteConfig = mockk<FirebaseRemoteConfig>()
    private val testDispatcher = StandardTestDispatcher()
    private val remoteConfigManager = RemoteConfigManagerImpl(
        mockFirebaseRemoteConfig,
        testDispatcher
    )

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

    @Test
    fun `getBoolean should get boolean value from firebase`() {
        // GIVEN
        every { mockFirebaseRemoteConfig.getBoolean(KEY) } returns true

        // WHEN
        val actual = remoteConfigManager.getBoolean(KEY)

        // THEN
        assertThat(actual).isTrue()
    }

    @Test
    fun `getLong should get long value from firebase`() {
        // GIVEN
        val expected = 1000L
        every { mockFirebaseRemoteConfig.getLong(KEY) } returns expected

        // WHEN
        val actual = remoteConfigManager.getLong(KEY)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getInt should get int value from firebase`() {
        // GIVEN
        val expected = 123L
        every { mockFirebaseRemoteConfig.getLong(KEY) } returns expected

        // WHEN
        val actual = remoteConfigManager.getInt(KEY)

        // THEN
        assertThat(actual).isEqualTo(expected.toInt())
    }
}
