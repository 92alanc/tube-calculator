package com.alancamargo.tubecalculator.search.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.core.design.R as R2

internal enum class UiSearchError(
    @DrawableRes val iconRes: Int,
    @StringRes val messageRes: Int
) {

    MISSING_ORIGIN_OR_DESTINATION(
        iconRes = R2.drawable.ic_underground, // TODO
        messageRes = R.string.search_error_missing_origin_destination
    ),
    SAME_ORIGIN_AND_DESTINATION(
        iconRes = R2.drawable.ic_underground, // TODO
        messageRes = R.string.search_error_same_origin_destination
    ),
    NETWORK(
        iconRes = R2.drawable.ic_underground, // TODO
        messageRes = R2.string.message_network_error
    ),
    SERVER(
        iconRes = R2.drawable.ic_underground, // TODO
        messageRes = R2.string.message_server_error
    ),
    GENERIC(
        iconRes = R2.drawable.ic_underground, // TODO
        messageRes = R2.string.message_generic_error
    )
}
