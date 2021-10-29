package com.pavlo.fedor.compose.service.sotrage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.storage.LaunchId
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import com.pavlo.fedor.compose.service.getaway.api.base.error.BaseApiError

internal class LaunchesInMemoryStorage : LaunchesPageStorage {

    private var lastPage: Page<LaunchInfo>? = null

    override suspend fun get(): Page<LaunchInfo>? = lastPage

    override fun search(query: LaunchId): LaunchInfo {
        val id by query
        return lastPage?.entities
            ?.firstOrNull { it.id == id }
            ?: throw BaseApiError.ServerError.NotFound
    }

    override suspend fun set(value: Page<LaunchInfo>) {
        lastPage = value
    }
}
