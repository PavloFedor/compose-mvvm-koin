package com.pavlo.fedor.compose.service.getaway.api.launches

import com.google.gson.annotations.SerializedName

internal class RocketDto (
    @SerializedName("configuration") val rocketConfig: ConfigurationDto?
)

internal class ConfigurationDto(
    @SerializedName("image_url") val image: String?,
    @SerializedName("wiki_url") val wikiUrl: String?
)