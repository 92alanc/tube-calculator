package com.alancamargo.tubecalculator.fares.data.work

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val WORK_NAME = "rail_fares_cache"

internal class RailFaresCacheWorkSchedulerImpl @Inject constructor(
    private val context: Context
) : RailFaresCacheWorkScheduler {

    override fun scheduleRailFaresCacheBackgroundWork() {
        val workManager = WorkManager.getInstance(context)
        val request = PeriodicWorkRequestBuilder<RailFaresCacheWorker>(
            1,
            TimeUnit.DAYS
        ).build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
