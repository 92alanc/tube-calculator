package com.alancamargo.tubecalculator.core.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

private const val FILE_NAME = "tube_calculator_preferences"
private const val KEY = "test_key"

class PreferencesManagerImplTest {

    private val mockSharedPreferences = mockk<SharedPreferences>(relaxed = true)
    private val mockContext = mockk<Context> {
        every {
            getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        } returns mockSharedPreferences
    }

    private val preferencesManager = PreferencesManagerImpl(mockContext)

    @Test
    fun `getBoolean should get boolean value from shared preferences`() {
        // GIVEN
        every { mockSharedPreferences.getBoolean(KEY, false) } returns true

        // WHEN
        val actual = preferencesManager.getBoolean(KEY, defaultValue = false)

        // THEN
        assertThat(actual).isTrue()
    }

    @Test
    fun `putBoolean should put boolean value in shared preferences`() {
        // WHEN
        preferencesManager.putBoolean(KEY, value = true)

        // THEN
        verify { mockSharedPreferences.edit { putBoolean(KEY, true) } }
    }
}
