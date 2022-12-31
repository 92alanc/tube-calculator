package com.alancamargo.tubecalculator.fares.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FARES")
internal data class DbFareListRoot(
    @PrimaryKey val id: String,
    val originId: String,
    val destinationId: String,
    val jsonResponse: String,
    val creationDate: Long = System.currentTimeMillis()
)
