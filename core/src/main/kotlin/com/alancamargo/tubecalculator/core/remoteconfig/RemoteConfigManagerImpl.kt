package com.alancamargo.tubecalculator.core.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject

internal class RemoteConfigManagerImpl @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : RemoteConfigManager {

    override fun getDouble(key: String): Double {
        return firebaseRemoteConfig.getDouble(key)
    }
}
