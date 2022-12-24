package com.alancamargo.tubecalculator.common.domain.model

data class Station(
    val id: String,
    val name: String,
    val modes: List<Mode>,
    val zones: List<Int>
)
