package com.alancamargo.tubecalculator.common.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.core.design.R

enum class UiMode(
    @DrawableRes val iconRes: Int,
    @StringRes val contentDescriptionRes: Int
) {

    DLR(
        iconRes = R.drawable.ic_dlr,
        contentDescriptionRes = R.string.content_description_dlr
    ),
    ELIZABETH_LINE(
        iconRes = R.drawable.ic_elizabeth_line,
        contentDescriptionRes = R.string.content_description_elizabeth_line
    ),
    NATIONAL_RAIL(
        iconRes = R.drawable.ic_national_rail,
        contentDescriptionRes = R.string.content_description_national_rail
    ),
    OVERGROUND(
        iconRes = R.drawable.ic_overground,
        contentDescriptionRes = R.string.content_description_overground
    ),
    UNDERGROUND(
        iconRes = R.drawable.ic_underground,
        contentDescriptionRes = R.string.content_description_underground
    )
}
