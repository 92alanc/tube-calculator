package com.alancamargo.tubecalculator.core.remoteconfig

interface RemoteConfigManager {

    fun getDouble(key: String): Double

    fun getBoolean(key: String): Boolean

    fun getLong(key: String): Long

    fun getInt(key: String): Int
}
