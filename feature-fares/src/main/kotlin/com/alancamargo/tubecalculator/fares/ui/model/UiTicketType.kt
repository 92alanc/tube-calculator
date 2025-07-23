package com.alancamargo.tubecalculator.fares.ui.model

import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.fares.R

internal enum class UiTicketType(@StringRes val labelRes: Int) {

    PAY_AS_YOU_GO(R.string.fares_pay_as_you_go),
    CASH(R.string.fares_cash)
}
