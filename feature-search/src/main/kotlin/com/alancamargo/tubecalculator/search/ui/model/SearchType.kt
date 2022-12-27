package com.alancamargo.tubecalculator.search.ui.model

import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.search.R

internal enum class SearchType(
    @StringRes val labelRes: Int,
    @StringRes val hintRes: Int
) {

    ORIGIN(
        labelRes = R.string.search_where_are_you_travelling_from,
        hintRes = R.string.search_label_origin
    ),
    DESTINATION(
        labelRes = R.string.search_where_are_you_travelling_to,
        hintRes = R.string.search_label_destination
    )
}
