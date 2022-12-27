package com.alancamargo.tubecalculator.core.design.tools

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

    override fun showDialogue(context: Context, titleRes: Int, messageRes: Int) {
        val builder = getBuilder(
            context = context,
            titleRes = titleRes,
            messageRes = messageRes
        )

        builder.show()
    }

    private fun getBuilder(
        context: Context,
        titleRes: Int,
        messageRes: Int
    ): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(context)
            .setTitle(titleRes)
            .setMessage(messageRes)
            .setNeutralButton(R.string.ok, null)
    }
}
