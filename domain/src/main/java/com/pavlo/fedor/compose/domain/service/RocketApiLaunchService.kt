package com.pavlo.fedor.compose.domain.service

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.PageResult
import com.pavlo.fedor.compose.domain.model.PageRequest
import kotlinx.coroutines.flow.Flow
import java.util.*

interface RocketApiLaunchService {

    suspend fun get(query: String?, pageRequest: PageRequest): Flow<PageResult<LaunchInfo>>
}
