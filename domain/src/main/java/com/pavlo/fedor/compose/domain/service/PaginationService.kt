package com.pavlo.fedor.compose.domain.service

import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.model.PageResult
import kotlinx.coroutines.flow.Flow

interface PaginationService {

    suspend fun <Entity> onPageRequest(previousPage: Page<Entity>? = null): PageRequestExecutor<Entity>

    interface PageRequestExecutor<Entity> {

        suspend fun execute(action: suspend (PageRequest) -> Flow<PageResult<Entity>>): Flow<Page<Entity>>
    }
}
