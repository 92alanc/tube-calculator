package com.alancamargo.tubecalculator.core.design.tools

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.core.design.R

interface DialogueHelper {

    fun showDialogue(
        context: Context,
        @DrawableRes iconRes: Int,
        @StringRes titleRes: Int,
        @StringRes messageRes: Int
    )

    fun showDialogue(
        context: Context,
        @StringRes titleRes: Int,
        @StringRes messageRes: Int,
        @StringRes buttonTextRes: Int = R.string.ok,
        onDismiss: (() -> Unit)? = null
    )

    fun showDialogue(
        context: Context,
        @StringRes titleRes: Int,
        message: CharSequence
    )
}
