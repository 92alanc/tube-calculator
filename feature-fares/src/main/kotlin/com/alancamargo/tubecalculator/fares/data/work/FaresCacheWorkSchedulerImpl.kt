package com.alancamargo.tubecalculator.fares.data.work

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val WORK_NAME = "fares_cache"

internal class FaresCacheWorkSchedulerImpl @Inject constructor(
    private val workManager: WorkManager
) : FaresCacheWorkScheduler {

    override fun scheduleFaresCacheBackgroundWork() {
        val request = PeriodicWorkRequestBuilder<FaresCacheWorker>(
            15,
            TimeUnit.MINUTES
        ).build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
