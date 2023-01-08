package com.alancamargo.tubecalculator.core.design.dialogue

import android.content.Context
import com.alancamargo.tubecalculator.core.design.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

internal class DialogueHelperImpl @Inject constructor() : DialogueHelper {

    override fun showDialogue(
        context: Context,
        iconRes: Int,
        titleRes: Int,
        messageRes: Int
    ) {
        val builder = getBuilder(
            context = context,
            titleRes = titleRes,
            messageRes = messageRes
        )

        builder.setIcon(iconRes).show()
    }

    override fun showDialogue(
        context: Context,
        titleRes: Int,
        messageRes: Int,
        buttonTextRes: Int,
        onDismiss: (() -> Unit)?
    ) {
        val builder = getBuilder(
            context = context,
            titleRes = titleRes,
            messageRes = messageRes,
            buttonTextRes = buttonTextRes,
            onDismiss = onDismiss
        )

        builder.show()
    }

    override fun showDialogue(context: Context, titleRes: Int, message: CharSequence) {
        val builder = getBuilder(
            context = context,
            titleRes = titleRes,
            message = message
        )

        builder.show()
    }

    private fun getBuilder(
        context: Context,
        titleRes: Int,
        messageRes: Int,
        buttonTextRes: Int = R.string.ok,
        onDismiss: (() -> Unit)? = null
    ): MaterialAlertDialogBuilder {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(titleRes)
            .setMessage(messageRes)
            .setCancelable(false)

        onDismiss?.let {
            builder.setNeutralButton(buttonTextRes) { dialogue, _ ->
                it()
                dialogue.dismiss()
            }
        } ?: run {
            builder.setNeutralButton(R.string.ok, null)
        }

        return builder
    }

    private fun getBuilder(
        context: Context,
        titleRes: Int,
        message: CharSequence
    ): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(context)
            .setTitle(titleRes)
            .setMessage(message)
            .setNeutralButton(R.string.ok, null)
            .setCancelable(false)
    }
}
