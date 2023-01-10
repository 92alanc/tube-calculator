package com.alancamargo.tubecalculator.core.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject

internal class RemoteConfigManagerImpl @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : RemoteConfigManager {

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
