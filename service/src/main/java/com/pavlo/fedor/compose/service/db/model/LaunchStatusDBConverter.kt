package com.pavlo.fedor.compose.service.db.model

import androidx.room.TypeConverter

object LaunchStatusDBConverter {

    @TypeConverter
    fun to(status: LaunchStatusDb): Int = status.value

    @TypeConverter
    fun from(status: Int): LaunchStatusDb = LaunchStatusDb.values().first { it.value == status }
}