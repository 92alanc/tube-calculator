package com.alancamargo.tubecalculator.core.design.model

data class SwitchData(val isEnabled: Boolean, val onChanged: (Boolean) -> Unit)
