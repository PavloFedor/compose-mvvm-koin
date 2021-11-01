package com.pavlo.fedor.compose.service.sotrage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.storage.LaunchInfoIdFilter
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber

internal class LaunchesHistoryInMemoryStorage : LaunchesPageStorage {

    private val stateFlow = MutableStateFlow<StoragePage?>(null)

    override suspend fun get(): Flow<Page<LaunchInfo>?> = flowOf(stateFlow.value)

    override fun select(query: LaunchInfoIdFilter): Flow<LaunchInfo?> = flowOf(stateFlow.value?.entities ?: listOf())
        .map { it.firstOrNull(query::invoke) }
        .flowOn(Dispatchers.IO)

    override suspend fun set(value: Page<LaunchInfo>): Flow<Unit> = flowOf(value)
        .map { StoragePage(value.offset, value.total, value.entities.toMutableList(), value.isLastPage) }
        .map { newPage ->
            Timber.d("Emit page: $newPage")
            stateFlow.emit(newPage)
        }

    override suspend fun replace(value: LaunchInfo): Flow<Unit> = flowOf(stateFlow.value)
        .transform { page -> if (page != null) emit(page) }
        .map { page -> Triple(page, page.entities.indexOfFirst { value.id == it.id }, value) }
        .filter { (_, index, _) -> index >= 0 }
        .map { (page, index, newInfo) -> page.copy(entities = page.entities.toMutableList().also { it[index] = newInfo }) }
        .map { newPage -> stateFlow.emit(newPage) }
        .flowOn(Dispatchers.IO)

    override fun observe(): Flow<Page<LaunchInfo>> = stateFlow.transform { page ->
        Timber.d("newPage $page")
        if (page != null) emit(page)
    }

    private data class StoragePage(
        override val offset: Long,
        override val total: Long,
        override val entities: List<LaunchInfo>,
        override val isLastPage: Boolean
    ) : Page<LaunchInfo> {
        override fun toString(): String = "offset: $offset; total: $total; isLast: $isLastPage; count: ${entities.size}"

        override fun equals(other: Any?): Boolean = this === other
        override fun hashCode(): Int = javaClass.hashCode()
    }
}
