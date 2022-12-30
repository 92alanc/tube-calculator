package com.alancamargo.tubecalculator.fares.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "QUERIES")
internal data class DbQuery(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val originId: String,
    val destinationId: String
)
