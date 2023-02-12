package com.alancamargo.tubecalculator.core.extensions

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : Parcelable> AppCompatActivity.args(): Lazy<T> = lazy {
    intent.getArguments()
}
