package com.alancamargo.tubecalculator.core.extensions

import retrofit2.Response

fun <T> Response<T>.isRequestError(): Boolean {
    val range = 400..499
    return range.contains(code())
}

fun <T> Response<T>.isServerError(): Boolean {
    val range = 500..599
    return range.contains(code())
}
