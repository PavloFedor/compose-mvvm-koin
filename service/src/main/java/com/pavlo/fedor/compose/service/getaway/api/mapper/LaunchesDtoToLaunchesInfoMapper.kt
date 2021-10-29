package com.pavlo.fedor.compose.service.getaway.api.mapper

import com.example.utils.mapper.Mapper
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.model.PageResult
import com.pavlo.fedor.compose.service.getaway.api.launches.LaunchesDto
import com.pavlo.fedor.compose.service.getaway.api.mapper.LaunchesDtoToLaunchesInfoMapper.MapperParams


internal class LaunchesDtoToLaunchesInfoMapper(
    private val launchInfoMapper: LaunchDtoToLaunchInfoMapper
) : Mapper<MapperParams, LaunchesDto, PageResult<LaunchInfo>> {

    override fun invoke(additionalParam: MapperParams, source: LaunchesDto): PageResult<LaunchInfo> {
        val favorites = additionalParam.favoriteLaunches.toMutableList()
        val entities = source.launches.map { launchDto ->
            val isFavorite = favorites.firstOrNull { it.id == launchDto.id }
                ?.apply { favorites.remove(this) } != null
            launchInfoMapper(IsFavorite(isFavorite), launchDto)
        }
        return PageResult(
            offset = additionalParam.request.offset,
            total = source.total,
            entities = entities
        )
    }

    class MapperParams(val request: PageRequest, val favoriteLaunches: List<LaunchInfo>)
}