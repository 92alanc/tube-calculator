package com.alancamargo.tubecalculator.search.data.model

internal enum class ModeResponse(val string: String) {

    DLR(string = "dlr"),
    ELIZABETH_LINE(string = "elizabeth-line"),
    NATIONAL_RAIL(string = "national-rail"),
    OVERGROUND(string = "overground"),
    UNDERGROUND(string = "tube");

    companion object {
        fun fromString(string: String) = when (string) {
            DLR.string -> DLR
            ELIZABETH_LINE.string -> ELIZABETH_LINE
            NATIONAL_RAIL.string -> NATIONAL_RAIL
            OVERGROUND.string -> OVERGROUND
            UNDERGROUND.string -> UNDERGROUND
            else -> throw IllegalArgumentException("Invalid mode")
        }
    }
}
