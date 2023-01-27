package com.alancamargo.tubecalculator.fares.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RAIL_FARES")
internal data class DbRailFare(
    @PrimaryKey val id: String,
    val originId: String,
    val destinationId: String,
    val jsonResponse: String
)
