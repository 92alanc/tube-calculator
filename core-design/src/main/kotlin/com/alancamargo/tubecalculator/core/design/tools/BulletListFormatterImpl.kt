package com.alancamargo.tubecalculator.core.design.tools

import android.text.Spannable
import android.text.TextUtils
import android.text.style.BulletSpan
import androidx.core.text.toSpannable
import javax.inject.Inject

internal class BulletListFormatterImpl @Inject constructor() : BulletListFormatter {

    override fun getBulletList(strings: List<String>): CharSequence {
        val bullets = strings.map { rawText ->
            rawText.toSpannable().apply {
                setSpan(BulletSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }.toTypedArray()

        return TextUtils.concat(*bullets)
    }
}
