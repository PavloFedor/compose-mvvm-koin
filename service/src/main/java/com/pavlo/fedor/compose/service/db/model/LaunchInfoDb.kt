package com.pavlo.fedor.compose.service.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pavlo.fedor.compose.service.getaway.api.mapper.IsFavorite

@Entity(tableName = "launch_info")
internal data class LaunchInfoDb(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "launch_name") val name: String,
    @ColumnInfo(name = "info_page") val infoPage: String,
    @ColumnInfo(name = "country_flag_link") val countryFlagLink: String,
    @ColumnInfo(name = "launch_status") val status: LaunchStatusDb,
    @ColumnInfo(name = "launch_date") val date: Long,
    @ColumnInfo(name = "image_url") val image: String?,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
)
