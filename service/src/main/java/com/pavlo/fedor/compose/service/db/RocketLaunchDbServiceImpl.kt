package com.pavlo.fedor.compose.service.db

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.model.PageResult
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService
import com.pavlo.fedor.compose.service.db.dao.LaunchesDao
import com.pavlo.fedor.compose.service.db.mapper.LaunchInfoDbToLaunchInfoMapper
import com.pavlo.fedor.compose.service.db.mapper.LaunchInfoToLaunchInfoDbMapper
import com.pavlo.fedor.compose.service.db.mapper.LaunchesDbPageToLaunchesPageResultMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

internal class RocketLaunchDbServiceImpl(
    private val launchesDao: LaunchesDao,
    private val launchesDbPageToLaunchesPageResultMapper: LaunchesDbPageToLaunchesPageResultMapper,
    private val launchInfoDbToLaunchInfoMapper: LaunchInfoDbToLaunchInfoMapper,
    private val launchInfoToLaunchInfoDbMapper: LaunchInfoToLaunchInfoDbMapper
) : RocketLaunchDbService {

    override suspend fun get(query: String?, pageRequest: PageRequest) = flow {
        Timber.d("query $query")
        val dbPage = launchesDao.getLaunchesPage(limit = pageRequest.limit, offset = pageRequest.offset, query = query)
        emit(launchesDbPageToLaunchesPageResultMapper(pageRequest, dbPage))
    }.flowOn(Dispatchers.IO)

    override suspend fun get(ids: RocketLaunchDbService.LaunchIds) = flow {
        emit(launchesDao.getLaunches(ids.ids).map { launchInfoDbToLaunchInfoMapper(Unit, it) })
    }.flowOn(Dispatchers.IO)

    override suspend fun set(launchInfo: LaunchInfo) = flow {
        emit(launchesDao.save(launchInfoToLaunchInfoDbMapper(Unit, launchInfo)))
    }.flowOn(Dispatchers.IO)

    override suspend fun getFavorite(pageRequest: PageRequest) = flow {
        val dbPage = launchesDao.getFavoriteLaunchesPage(limit = pageRequest.limit, offset = pageRequest.offset)
        emit(launchesDbPageToLaunchesPageResultMapper(pageRequest, dbPage))
    }.flowOn(Dispatchers.IO)

    override suspend fun set(launchInfo: List<LaunchInfo>): Flow<Unit> = flow {
        emit(launchesDao.save(launchInfo.map { launchInfo -> launchInfoToLaunchInfoDbMapper(Unit, launchInfo) }))
    }.flowOn(Dispatchers.IO)
}