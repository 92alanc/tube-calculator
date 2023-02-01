package com.alancamargo.tubecalculator.fares.data.work

import android.content.Context
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
internal class RailFaresCacheWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val remoteConfigManager: RemoteConfigManager,
    private val localDataSource: FaresLocalDataSource
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val shouldClearCache = remoteConfigManager.getBoolean(REMOTE_CONFIG_KEY)

        if (shouldClearCache) {
            localDataSource.clearCache()
        }

        Result.success()
    }
}
