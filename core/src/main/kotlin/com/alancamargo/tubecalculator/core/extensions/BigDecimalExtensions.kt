package com.alancamargo.tubecalculator.core.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.roundUpAsMoney(): String = setScale(2, RoundingMode.HALF_UP).toPlainString()
