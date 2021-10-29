package com.pavlo.fedor.compose.domain.service

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.model.PageResult
import kotlinx.coroutines.flow.Flow


interface RocketLaunchDbService {

    suspend fun get(pageRequest: PageRequest): Flow<PageResult<LaunchInfo>>

    suspend fun get(ids: LaunchIds): Flow<List<LaunchInfo>>

    suspend fun set(launchInfo: LaunchInfo): Flow<Unit>

    suspend fun delete(launchInfo: LaunchInfo): Flow<Unit>

    @JvmInline
    value class LaunchIds(val ids: List<String>)
}
