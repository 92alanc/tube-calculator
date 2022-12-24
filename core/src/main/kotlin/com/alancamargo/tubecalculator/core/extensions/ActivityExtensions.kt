package com.alancamargo.tubecalculator.core.extensions

import android.os.Build
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alancamargo.tubecalculator.core.constants.EXTRA_ARGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <reified T : Parcelable> AppCompatActivity.args(): Lazy<T> = lazy {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(EXTRA_ARGS, T::class.java)
    } else {
        @Suppress("DEPRECATION") // Deprecated from API 33 onwards
        intent.getParcelableExtra(EXTRA_ARGS)
    } ?: throw IllegalStateException("Args not provided")
}

fun <T> AppCompatActivity.observeViewModelFlow(flow: Flow<T>, block: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                block(it)
            }
        }
    }
}
