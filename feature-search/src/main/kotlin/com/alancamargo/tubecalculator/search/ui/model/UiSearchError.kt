package com.alancamargo.tubecalculator.search.ui.model

import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.core.design.R as R2

internal enum class UiSearchError(@StringRes val messageRes: Int) {

    SAME_ORIGIN_AND_DESTINATION(messageRes = R.string.search_error_same_origin_destination),
    GENERIC(messageRes = R2.string.message_generic_error)
}
