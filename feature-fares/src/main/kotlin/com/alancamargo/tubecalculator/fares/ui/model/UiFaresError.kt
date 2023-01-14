package com.alancamargo.tubecalculator.fares.ui.model

import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.core.design.R as R2

enum class UiFaresError(@StringRes val messageRes: Int) {

    INVALID_QUERY(messageRes = R.string.fares_message_invalid_query_error),
    NETWORK(messageRes = R2.string.message_network_error),
    SERVER(messageRes = R2.string.message_server_error),
    GENERIC(messageRes = R2.string.message_generic_error)
}
