package com.pavlo.fedor.compose.domain.service

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.model.PageResult
import com.pavlo.fedor.compose.domain.storage.LaunchesListFilter
import kotlinx.coroutines.flow.Flow


interface RocketLaunchDbService {

    suspend fun get(query: String?, pageRequest: PageRequest): Flow<PageResult<LaunchInfo>>

    suspend fun get(ids: LaunchIds): Flow<List<LaunchInfo>>

    suspend fun set(launchInfo: LaunchInfo): Flow<Unit>

    suspend fun set(launchInfo: List<LaunchInfo>): Flow<Unit>

    suspend fun getFavorite(pageRequest: PageRequest): Flow<PageResult<LaunchInfo>>

    @JvmInline
    value class LaunchIds(val ids: List<String>)
}
