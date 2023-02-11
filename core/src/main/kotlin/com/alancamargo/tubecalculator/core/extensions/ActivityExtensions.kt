package com.alancamargo.tubecalculator.core.extensions

import android.os.Build
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.core.constants.EXTRA_ARGS

inline fun <reified T : Parcelable> AppCompatActivity.args(): Lazy<T> = lazy {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(EXTRA_ARGS, T::class.java)
    } else {
        @Suppress("DEPRECATION") // Deprecated from API 33 onwards
        intent.getParcelableExtra(EXTRA_ARGS)!!
    } ?: throw IllegalStateException("Args not provided")
}
