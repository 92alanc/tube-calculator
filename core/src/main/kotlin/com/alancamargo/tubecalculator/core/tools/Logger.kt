package com.alancamargo.tubecalculator.core.tools

interface Logger {

    fun debug(message: String)

    fun error(throwable: Throwable)
}
