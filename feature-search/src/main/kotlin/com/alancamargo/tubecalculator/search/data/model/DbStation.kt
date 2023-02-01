package com.alancamargo.tubecalculator.search.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "STATIONS")
internal data class DbStation(
    @PrimaryKey @ColumnInfo(name = "ID") val id: String,
    @ColumnInfo(name = "NAME") val name: String,
    @ColumnInfo(name = "MODES") val modesJson: String
)
