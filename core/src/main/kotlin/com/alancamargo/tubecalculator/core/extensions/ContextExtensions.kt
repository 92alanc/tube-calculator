package com.alancamargo.tubecalculator.core.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

fun <T : AppCompatActivity> Context.createIntent(activityClass: KClass<T>): Intent {
    return Intent(this, activityClass.java)
}

fun Context.getVersionName(): String {
    val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION")
        packageManager.getPackageInfo(packageName, 0)
    }

    return packageInfo.versionName
}
