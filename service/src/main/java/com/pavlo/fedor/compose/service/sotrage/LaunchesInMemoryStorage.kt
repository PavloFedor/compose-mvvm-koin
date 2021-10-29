package com.pavlo.fedor.compose.service.sotrage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.storage.LaunchId
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import com.pavlo.fedor.compose.service.getaway.api.base.error.BaseApiError
import com.pavlo.fedor.compose.service.pagination.OneSidePaginationService

internal class LaunchesInMemoryStorage : LaunchesPageStorage {

    private var lastPage: StoragePage? = null

    override suspend fun get(): Page<LaunchInfo>? = lastPage

    override fun search(query: LaunchId): LaunchInfo {
        val id by query
        return lastPage?.entities
            ?.firstOrNull { it.id == id }
            ?: throw BaseApiError.ServerError.NotFound
    }

    override suspend fun set(value: Page<LaunchInfo>) {
        lastPage = StoragePage(
            offset = value.offset,
            total = value.total,
            entities = value.entities.toMutableList(),
            isLastPage = value.isLastPage
        )
    }

    override fun replace(launchInfo: LaunchInfo) {
        lastPage?.entities?.also { entities ->
            entities.indexOfFirst { it.id == launchInfo.id }
                .takeIf { it != -1 }
                ?.also { entities[it] = launchInfo }
        }
    }

    private class StoragePage(
        override val offset: Long,
        override val total: Long,
        override val entities: MutableList<LaunchInfo>,
        override val isLastPage: Boolean
    ) : Page<LaunchInfo>
}
