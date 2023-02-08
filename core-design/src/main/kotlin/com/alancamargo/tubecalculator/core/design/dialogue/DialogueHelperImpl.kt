package com.alancamargo.tubecalculator.core.design.dialogue

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.alancamargo.tubecalculator.core.design.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
class DialogueHelperImpl @Inject internal constructor() : DialogueHelper {

    override fun showDialogue(
        context: Context,
        iconRes: Int,
        title: String,
        messageRes: Int
    ) {
        val message = context.getString(messageRes)

        val builder = getBuilder(
            context = context,
            title = title,
            message = message
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
        val title = context.getString(titleRes)
        val message = context.getString(messageRes)

        val builder = getBuilder(
            context = context,
            title = title,
            message = message,
            buttonTextRes = buttonTextRes,
            onDismiss = onDismiss
        )

        builder.show()
    }

    override fun showDialogue(context: Context, titleRes: Int, message: CharSequence) {
        val title = context.getString(titleRes)

        val builder = getBuilder(
            context = context,
            title = title,
            message = message
        )

        builder.show()
    }

    private fun getBuilder(
        context: Context,
        title: String,
        message: CharSequence,
        buttonTextRes: Int = R.string.ok,
        onDismiss: (() -> Unit)? = null
    ): MaterialAlertDialogBuilder {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
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
}
