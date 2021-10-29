package com.pavlo.fedor.compose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchInfo(
    val id: String,
    val name: String,
    val infoPage: String,
    val imageUrl: String?,
    val countryFlagLink: String,
    val status: LaunchStatus,
    val date: Long,
    val isFavorite: Boolean
) : Parcelable

enum class LaunchStatus {
    FAILED,
    SUCCEED,
    IN_PROGRESS
}