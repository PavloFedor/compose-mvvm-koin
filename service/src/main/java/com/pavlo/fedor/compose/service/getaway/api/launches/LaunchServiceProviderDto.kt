package com.pavlo.fedor.compose.service.getaway.api.launches

import com.google.gson.annotations.SerializedName

internal class LaunchServiceProviderDto(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("wiki_url") val wikiUrl: String?,
    @SerializedName("nation_url") val flagUrl: String?
)
