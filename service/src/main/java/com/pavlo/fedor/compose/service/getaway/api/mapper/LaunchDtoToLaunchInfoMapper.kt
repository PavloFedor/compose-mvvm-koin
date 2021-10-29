package com.pavlo.fedor.compose.service.getaway.api.mapper

import com.example.utils.mapper.Mapper
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.LaunchStatus
import com.pavlo.fedor.compose.service.getaway.api.launches.LaunchDto
import com.pavlo.fedor.compose.service.getaway.api.launches.LaunchDto.Companion.SUCCESS_LAUNCH
import kotlin.reflect.KProperty

@JvmInline
value class IsFavorite(private val value: Boolean) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
}

internal class LaunchDtoToLaunchInfoMapper : Mapper<IsFavorite, LaunchDto, LaunchInfo> {

    override fun invoke(isFavorite: IsFavorite, source: LaunchDto): LaunchInfo {
        val favorite by isFavorite

        val imageUrl = source.image
            ?: source.rocketInfo?.rocketConfig?.image
            ?: source.serviceProvider?.imageUrl

        val flagUrl = source.serviceProvider?.flagUrl
        val wiki = source.rocketInfo?.rocketConfig?.wikiUrl
            ?: source.serviceProvider?.wikiUrl
            ?: source.place.wikiUrl
            ?: DEFAULT_WIKI_PAGE


        val status = when (source.status.id) {
            3 -> LaunchStatus.SUCCEED
            4, 7 -> LaunchStatus.FAILED
            else -> LaunchStatus.IN_PROGRESS
        }

        return LaunchInfo(
            id = source.id,
            name = source.name,
            status = status,
            date = source.launchDate.time,
            isFavorite = favorite,
            imageUrl = imageUrl,
            countryFlagLink = flagUrl ?: "",
            infoPage = wiki,
        )
    }

    companion object {
        private const val DEFAULT_WIKI_PAGE = "https://en.wikipedia.org/wiki/Timeline_of_spaceflight"
    }

}