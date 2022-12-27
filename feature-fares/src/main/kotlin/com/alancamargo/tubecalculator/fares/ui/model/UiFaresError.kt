package com.alancamargo.tubecalculator.fares.ui.model

import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.core.design.R

enum class UiFaresError(@StringRes val messageRes: Int) {

    NETWORK(messageRes = R.string.message_network_error),
    SERVER(messageRes = R.string.message_server_error),
    GENERIC(messageRes = R.string.message_generic_error)
}
