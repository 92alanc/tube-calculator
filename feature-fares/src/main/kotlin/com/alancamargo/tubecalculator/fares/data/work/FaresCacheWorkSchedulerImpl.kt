package com.alancamargo.tubecalculator.fares.data.work

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val WORK_NAME = "fares_cache"

internal class FaresCacheWorkSchedulerImpl @Inject constructor(
    private val context: Context
) : FaresCacheWorkScheduler {

    override fun scheduleFaresCacheBackgroundWork() {
        val workManager = WorkManager.getInstance(context)
        val request = PeriodicWorkRequestBuilder<FaresCacheWorker>(
            30,
            TimeUnit.DAYS
        ).build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
