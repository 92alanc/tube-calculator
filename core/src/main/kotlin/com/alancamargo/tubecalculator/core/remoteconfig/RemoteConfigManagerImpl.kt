package com.alancamargo.tubecalculator.core.remoteconfig

import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class RemoteConfigManagerImpl @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteConfigManager {

    override fun init() {
        CoroutineScope(dispatcher).launch {
            firebaseRemoteConfig.fetchAndActivate().await()
        }
    }

    override fun getDouble(key: String): Double {
        return firebaseRemoteConfig.getDouble(key)
    }

    override fun getBoolean(key: String): Boolean {
        return firebaseRemoteConfig.getBoolean(key)
    }

    override fun getLong(key: String): Long {
        return firebaseRemoteConfig.getLong(key)
    }

    override fun getInt(key: String): Int {
        return firebaseRemoteConfig.getLong(key).toInt()
    }
}
