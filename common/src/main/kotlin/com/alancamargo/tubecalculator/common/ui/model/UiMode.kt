package com.alancamargo.tubecalculator.common.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class UiMode(
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int
) {

    DLR(
        iconRes = 0,
        labelRes = 0
    ),
    ELIZABETH_LINE(
        iconRes = 0,
        labelRes = 0
    ),
    NATIONAL_RAIL(
        iconRes = 0,
        labelRes = 0
    ),
    OVERGROUND(
        iconRes = 0,
        labelRes = 0
    ),
    UNDERGROUND(
        iconRes = 0,
        labelRes = 0
    )
}
