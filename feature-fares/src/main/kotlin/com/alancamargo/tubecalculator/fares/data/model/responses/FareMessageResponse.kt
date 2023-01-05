package com.alancamargo.tubecalculator.fares.data.model.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FareMessageResponse(@SerialName("messageText") val message: String = "")
