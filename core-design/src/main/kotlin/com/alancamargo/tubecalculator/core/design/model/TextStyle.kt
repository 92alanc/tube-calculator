package com.alancamargo.tubecalculator.core.design.model

import androidx.annotation.ColorRes
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.alancamargo.tubecalculator.core.design.R

enum class TextStyle(
    val fontWeight: FontWeight = FontWeight.Normal,
    val fontSize: TextUnit = TextUnit.Unspecified,
    @ColorRes val textColourRes: Int = R.color.black,
) {

    HEADLINE_1(fontSize = 20.sp),
    HEADLINE_2(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    HEADLINE_3(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    BODY,
    CAPTION(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        textColourRes = R.color.grey_dark
    )
}
