package com.alancamargo.tubecalculator.search.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class ModeResponse {

    @SerialName("bus")
    BUS,

    @SerialName("cable-car")
    CABLE_CAR,

    @SerialName("dlr")
    DLR,

    @SerialName("elizabeth-line")
    ELIZABETH_LINE,

    @SerialName("international-rail")
    INTERNATIONAL_RAIL,

    @SerialName("national-rail")
    NATIONAL_RAIL,

    @SerialName("overground")
    OVERGROUND,

    @SerialName("plane")
    PLANE,

    @SerialName("tram")
    TRAM,

    @SerialName("tube")
    UNDERGROUND
}
