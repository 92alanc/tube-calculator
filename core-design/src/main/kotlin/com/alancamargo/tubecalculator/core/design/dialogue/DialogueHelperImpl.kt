package com.alancamargo.tubecalculator.core.design.dialogue

import android.content.Context
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import com.alancamargo.tubecalculator.core.design.R
import com.alancamargo.tubecalculator.core.design.extensions.loadGif
import com.alancamargo.tubecalculator.core.tools.FileHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
class DialogueHelperImpl @Inject internal constructor(
    private val fileHelper: FileHelper
) : DialogueHelper {

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
        ).apply {
            setNeutralButton(R.string.ok, null)
            setIcon(iconRes)
        }

        builder.show()
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
            message = message
        )

        onDismiss?.let {
            builder.setNeutralButton(buttonTextRes) { dialogue, _ ->
                it()
                dialogue.dismiss()
            }
        } ?: run {
            builder.setNeutralButton(R.string.ok, null)
        }

        builder.show()
    }

    override fun showDialogue(
        context: Context,
        titleRes: Int,
        messageRes: Int,
        illustrationAssetName: String
    ) {
        val title = context.getString(titleRes)
        val message = context.getString(messageRes)

        val builder = getBuilder(
            context = context,
            title = title,
            message = message
        ).apply {
            val gifBytes = fileHelper.getBytesFromAsset(illustrationAssetName)

            val imageView = ImageView(context).also {
                it.loadGif(gifBytes)
            }

            setView(imageView)
            setNeutralButton(R.string.ok, null)
        }

        builder.show()
    }

    override fun showDialogue(
        context: Context,
        titleRes: Int,
        messageRes: Int,
        positiveButtonTextRes: Int,
        onPositiveButtonClick: () -> Unit,
        negativeButtonTextRes: Int,
        onNegativeButtonClick: () -> Unit
    ) {
        val title = context.getString(titleRes)
        val message = context.getString(messageRes)

        val builder = getBuilder(
            context = context,
            title = title,
            message = message
        ).apply {
            setPositiveButton(positiveButtonTextRes) { dialogue, _ ->
                onPositiveButtonClick()
                dialogue.dismiss()
            }

            setNegativeButton(negativeButtonTextRes) { dialogue, _ ->
                onNegativeButtonClick()
                dialogue.dismiss()
            }
        }

        builder.show()
    }

    override fun showDialogue(context: Context, titleRes: Int, message: CharSequence) {
        val title = context.getString(titleRes)

        val builder = getBuilder(
            context = context,
            title = title,
            message = message
        ).apply {
            setNeutralButton(R.string.ok, null)
        }

        builder.show()
    }

    private fun getBuilder(
        context: Context,
        title: String,
        message: CharSequence
    ): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
    }
}
