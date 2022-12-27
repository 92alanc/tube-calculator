package com.alancamargo.tubecalculator.common.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiStation(
    val id: String,
    val name: String,
    val modes: List<UiMode>
) : Parcelable
