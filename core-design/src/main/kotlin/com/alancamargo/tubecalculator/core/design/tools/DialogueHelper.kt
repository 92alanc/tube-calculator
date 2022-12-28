package com.alancamargo.tubecalculator.core.design.tools

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

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
        onDismiss: (() -> Unit)? = null
    )

    fun showDialogue(
        context: Context,
        @StringRes titleRes: Int,
        message: CharSequence
    )
}
