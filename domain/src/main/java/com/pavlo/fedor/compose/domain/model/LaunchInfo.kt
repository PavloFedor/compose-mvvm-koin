package com.pavlo.fedor.compose.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchInfo(
    val id: Long,
    val name: String,
    val infoPage: String,
    val imageUrl: String,
    val countryFlagLink: String,
    val isSucceed: Boolean,
    val date: Long,
    val isFavorite: Boolean
) : Parcelable
