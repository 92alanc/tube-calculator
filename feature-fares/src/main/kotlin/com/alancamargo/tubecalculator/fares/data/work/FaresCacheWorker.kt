package com.alancamargo.tubecalculator.fares.data.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val REMOTE_CONFIG_KEY = "should_clear_cache"

internal class FaresCacheWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        /*val shouldClearCache = remoteConfigManager.getBoolean(REMOTE_CONFIG_KEY)

        if (shouldClearCache) {
            localDataSource.clearCache()
        }*/
        Log.d("TEST_ALAN", "Worker running...")

        Result.success()
    }
}
