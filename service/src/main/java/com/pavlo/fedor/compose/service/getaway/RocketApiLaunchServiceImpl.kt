package com.pavlo.fedor.compose.service.getaway

import com.pavlo.fedor.compose.core.DatePattern
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.PageResult
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.service.RocketApiLaunchService
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService
import com.pavlo.fedor.compose.service.getaway.api.GetawayToDomainMapper
import com.pavlo.fedor.compose.service.getaway.api.launches.LaunchesApi
import com.pavlo.fedor.compose.service.getaway.api.launches.LaunchesDto
import com.pavlo.fedor.compose.service.getaway.api.mapper.LaunchesDtoToLaunchesInfoMapper
import com.pavlo.fedor.compose.service.getaway.api.mapper.LaunchesDtoToLaunchesInfoMapper.MapperParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.*

internal class RocketApiLaunchServiceImpl(
    private val launchesApi: LaunchesApi,
    private val launchesInfoMapper: LaunchesDtoToLaunchesInfoMapper,
    private val dbService: RocketLaunchDbService,
    apiErrorToDomainMapper: GetawayToDomainMapper
) : RocketApiLaunchService, RetrofitApiService(apiErrorToDomainMapper) {

    override suspend fun get(query: String?, pageRequest: PageRequest): Flow<PageResult<LaunchInfo>> = flowOf(Pair(query, pageRequest))
        .map { (search, request) -> request to launchesApi.get(search, request) }
        .flatMapConcat { (request, result) -> result.toMapperParams(request) }
        .map { (params, result) -> launchesInfoMapper(params, result) }
        .flowOn(Dispatchers.IO)

    private suspend fun LaunchesApi.get(query: String?, request: PageRequest): LaunchesDto = execute {
        val date = DatePattern.ISO_DATE_FORMAT(date = Date(System.currentTimeMillis()))
        query(search = query, limit = request.limit, offset = request.offset, date = date)
    }

    private suspend fun LaunchesDto.toMapperParams(request: PageRequest): Flow<Pair<MapperParams, LaunchesDto>> {
        return dbService.get(RocketLaunchDbService.LaunchIds(launches.map { it.id }))
            .map { favorites -> MapperParams(request, favorites) to this }
    }
}
