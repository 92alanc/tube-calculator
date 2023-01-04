package com.alancamargo.tubecalculator.fares.data.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.fares.data.local.FaresLocalDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val REMOTE_CONFIG_KEY = "should_clear_cache"

@HiltWorker
internal class FaresCacheWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val remoteConfigManager: RemoteConfigManager,
    private val localDataSource: FaresLocalDataSource
) : CoroutineWorker(context, workerParameters) {

    init {
        Log.d("TEST_ALAN", "Worker created")
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val shouldClearCache = remoteConfigManager.getBoolean(REMOTE_CONFIG_KEY)
        Log.d("TEST_ALAN", "Remote: ${remoteConfigManager.hashCode()}")
        Log.d("TEST_ALAN", "Local: ${localDataSource.hashCode()}")

        if (shouldClearCache) {
            localDataSource.clearCache()
        }

        Result.success()
    }
}
