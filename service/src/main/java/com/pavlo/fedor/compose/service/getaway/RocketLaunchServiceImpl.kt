package com.pavlo.fedor.compose.service.getaway

import com.pavlo.fedor.compose.core.DatePattern
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.PageResult
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.service.RocketLaunchService
import com.pavlo.fedor.compose.service.getaway.api.GetawayToDomainMapper
import com.pavlo.fedor.compose.service.getaway.api.launches.LaunchesApi
import com.pavlo.fedor.compose.service.getaway.api.mapper.LaunchesDtoToLaunchesInfoMapper
import com.pavlo.fedor.compose.service.getaway.api.mapper.LaunchesDtoToLaunchesInfoMapper.MapperParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*

internal class RocketLaunchServiceImpl(
    private val launchesApi: LaunchesApi,
    private val launchesInfoMapper: LaunchesDtoToLaunchesInfoMapper,
    apiErrorToDomainMapper: GetawayToDomainMapper
) : RocketLaunchService, RetrofitApiService(apiErrorToDomainMapper) {

    override suspend fun get(query: String?, pageRequest: PageRequest): Flow<PageResult<LaunchInfo>> = flow {
        val result = execute {
            val date = DatePattern.ISO_DATE_FORMAT(date = Date(System.currentTimeMillis()))
            launchesApi.query(search = query, limit = pageRequest.limit, offset = pageRequest.offset, date = date)
        }
        emit(launchesInfoMapper(MapperParams(pageRequest, listOf()), result))
    }.flowOn(Dispatchers.IO)
}
