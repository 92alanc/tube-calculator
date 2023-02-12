package com.alancamargo.tubecalculator.core.extensions

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import com.alancamargo.tubecalculator.core.constants.EXTRA_ARGS

fun <T : Parcelable> Intent.putArguments(args: T): Intent = putExtra(EXTRA_ARGS, args)

inline fun <reified T : Parcelable> Intent.getArguments(): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(EXTRA_ARGS, T::class.java)
    } else {
        @Suppress("DEPRECATION") // Deprecated from API 33 onwards
        getParcelableExtra(EXTRA_ARGS)!!
    } ?: throw IllegalStateException("Args not provided")
}
