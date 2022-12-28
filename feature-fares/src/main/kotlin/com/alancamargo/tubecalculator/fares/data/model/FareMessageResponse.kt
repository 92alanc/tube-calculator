package com.alancamargo.tubecalculator.fares.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FareMessageResponse(@SerialName("messageText") val message: String)
