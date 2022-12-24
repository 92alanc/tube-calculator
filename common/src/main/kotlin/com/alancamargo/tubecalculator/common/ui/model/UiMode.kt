package com.alancamargo.tubecalculator.common.ui.model

import androidx.annotation.DrawableRes
import com.alancamargo.tubecalculator.core.design.R

enum class UiMode(@DrawableRes val iconRes: Int) {

    DLR(iconRes = R.drawable.ic_dlr),
    ELIZABETH_LINE(iconRes = R.drawable.ic_elizabeth_line),
    NATIONAL_RAIL(iconRes = R.drawable.ic_national_rail),
    OVERGROUND(iconRes = R.drawable.ic_overground),
    UNDERGROUND(iconRes = R.drawable.ic_underground)
}
