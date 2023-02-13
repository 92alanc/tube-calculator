package com.alancamargo.tubecalculator.core.design.dialogue

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.core.design.R

interface DialogueHelper {

    fun showDialogue(
        context: Context,
        @DrawableRes iconRes: Int,
        title: String,
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
        @StringRes messageRes: Int,
        illustrationAssetName: String
    )

    fun showDialogue(
        context: Context,
        @StringRes titleRes: Int,
        @StringRes messageRes: Int,
        @StringRes positiveButtonTextRes: Int,
        onPositiveButtonClick: () -> Unit,
        @StringRes negativeButtonTextRes: Int,
        onNegativeButtonClick: () -> Unit
    )

    fun showDialogue(
        context: Context,
        @StringRes titleRes: Int,
        message: CharSequence
    )
}
