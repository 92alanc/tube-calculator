package com.alancamargo.tubecalculator.core.tools

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class FileHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileHelper {

    override fun getBytesFromAsset(assetFileName: String): ByteArray {
        val asset = context.assets.open(assetFileName)
        return asset.use {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            buffer
        }
    }
}
