package com.alancamargo.tubecalculator.core.design.text

import android.text.Spannable
import android.text.TextUtils
import android.text.style.BulletSpan
import androidx.core.text.toSpannable
import javax.inject.Inject

internal class BulletListFormatterImpl @Inject constructor() : BulletListFormatter {

    override fun getBulletList(strings: List<String>): CharSequence {
        val gapWidth = 16

        val bullets = strings.map { rawText ->
            "$rawText\n\n".toSpannable().apply {
                setSpan(
                    BulletSpan(gapWidth),
                    0,
                    rawText.lastIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

            }
        }.toTypedArray()

        return TextUtils.concat(*bullets)
    }
}
