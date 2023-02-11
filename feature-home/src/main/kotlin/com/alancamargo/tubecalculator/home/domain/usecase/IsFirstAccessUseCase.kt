package com.alancamargo.tubecalculator.home.domain.usecase

internal interface IsFirstAccessUseCase {

    operator fun invoke(): Boolean
}