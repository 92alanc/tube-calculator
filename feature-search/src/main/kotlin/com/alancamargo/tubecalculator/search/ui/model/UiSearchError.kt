package com.alancamargo.tubecalculator.search.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal enum class UiSearchError(
    @DrawableRes val iconRes: Int,
    @StringRes val messageRes: Int
) {

    // TODO
    MISSING_ORIGIN_OR_DESTINATION(
        iconRes = 0,
        messageRes = 0
    ),
    SAME_ORIGIN_AND_DESTINATION(
        iconRes = 0,
        messageRes = 0
    ),
    NETWORK(
        iconRes = 0,
        messageRes = 0
    ),
    SERVER(
        iconRes = 0,
        messageRes = 0
    ),
    GENERIC(
        iconRes = 0,
        messageRes = 0
    )
}
