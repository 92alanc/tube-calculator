package com.alancamargo.tubecalculator.fares.data.model.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "FARES",
    foreignKeys = [
        ForeignKey(
            entity = DbQuery::class,
            parentColumns = ["id"],
            childColumns = ["queryId"]
        )
    ]
)
internal data class DbFareListRoot(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val queryId: String,
    val jsonResponse: String
)
