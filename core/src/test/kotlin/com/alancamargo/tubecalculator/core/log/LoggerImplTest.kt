package com.alancamargo.tubecalculator.core.log

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

private const val TAG = "TUBE_CALCULATOR_LOG"
private const val LOG_MESSAGE = "message"

class LoggerImplTest {

    private val mockCrashlytics = mockk<FirebaseCrashlytics>(relaxed = true)
    private val logger = LoggerImpl(mockCrashlytics)

    @Before
    fun setUp() {
        mockkStatic(Log::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `setCrashLoggingEnabled should change setting on crashlytics`() {
        // WHEN
        logger.setCrashLoggingEnabled(isEnabled = true)

        // THEN
        verify { mockCrashlytics.setCrashlyticsCollectionEnabled(true) }
    }

    @Test
    fun `debug should log locally`() {
        // WHEN
        logger.debug(LOG_MESSAGE)

        // THEN
        verify { Log.d(TAG, LOG_MESSAGE) }
    }

    @Test
    fun `debug should log on crashlytics`() {
        // WHEN
        logger.debug(LOG_MESSAGE)

        // THEN
        verify { mockCrashlytics.log(LOG_MESSAGE) }
    }

    @Test
    fun `error should log locally`() {
        // GIVEN
        val exception = Throwable()

        // WHEN
        logger.error(exception)

        // THEN
        verify { Log.e(TAG, exception.message, exception) }
    }

    @Test
    fun `error should log on crashlytics`() {
        // GIVEN
        val exception = Throwable()

        // WHEN
        logger.error(exception)

        // THEN
        verify { mockCrashlytics.recordException(exception) }
    }
}
