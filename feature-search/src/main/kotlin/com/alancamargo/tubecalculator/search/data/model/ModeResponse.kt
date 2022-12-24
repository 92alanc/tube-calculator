package com.alancamargo.tubecalculator.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class ModeResponse {

    @SerialName("dlr")
    DLR,

    @SerialName("elizabeth-line")
    ELIZABETH_LINE,

    @SerialName("national-rail")
    NATIONAL_RAIL,

    @SerialName("overground")
    OVERGROUND,

    @SerialName("tube")
    UNDERGROUND
}
