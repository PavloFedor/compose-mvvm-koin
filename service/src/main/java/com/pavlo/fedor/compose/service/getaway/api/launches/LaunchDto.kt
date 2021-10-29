package com.pavlo.fedor.compose.service.getaway.api.launches

import com.google.gson.annotations.SerializedName
import java.util.*

internal class LaunchesDto(
    @SerializedName("count") val total: Long,
    @SerializedName("results") val launches: List<LaunchDto>
)

internal class LaunchDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("net") val launchDate: Date,
    @SerializedName("status") val status: StatusDto,
    @SerializedName("pad") val place: PadDto,
    @SerializedName("image") val image: String?,
    @SerializedName("rocket") val rocketInfo: RocketDto?,
    @SerializedName("launch_service_provider") val serviceProvider: LaunchServiceProviderDto?
) {
    companion object {
        const val SUCCESS_LAUNCH = 3
    }
}

//id: 3 - success, 4 and 7 - failed
internal class StatusDto(@SerializedName("id") val id: Int)

internal class PadDto(
    @SerializedName("location") val location: LocationDto,
    @SerializedName("wiki_url") val wikiUrl: String?
)

internal class LocationDto(@SerializedName("country_code") val countryCode: String)

