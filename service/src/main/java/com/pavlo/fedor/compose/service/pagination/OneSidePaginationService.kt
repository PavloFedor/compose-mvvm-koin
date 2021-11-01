package com.pavlo.fedor.compose.service.pagination

import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.model.PageResult
import com.pavlo.fedor.compose.domain.service.PaginationService
import com.pavlo.fedor.compose.domain.service.PaginationService.PageRequestExecutor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

internal class OneSidePaginationService(private val limit: Int = 50) : PaginationService {

    override suspend fun <Entity> onPageRequest(previousPage: Page<Entity>?): PageRequestExecutor<Entity> {
        val request = accuratePageRequest(previousPage = previousPage)
        Timber.d("Request: offset: ${request.offset};  limit:${request.limit}")
        return DefaultPageRequestExecutor(
            previousPage = previousPage,
            pageRequest = request
        )
    }

    private fun <Entity> accuratePageRequest(previousPage: Page<Entity>?): PageRequest {
        Timber.d("Previous: offset: ${previousPage?.offset}; total: ${previousPage?.total}; count:${previousPage?.entities?.size}")
        if (previousPage == null) return PageRequest(limit = limit, offset = 0)
        if (previousPage.isLastPage) return PageRequest(offset = previousPage.offset, limit = limit)

        return PageRequest(offset = previousPage.entities.size.toLong(), limit = limit)
    }

    private class DefaultPageRequestExecutor<Entity>(
        private val previousPage: Page<Entity>?,
        private val pageRequest: PageRequest
    ) : PageRequestExecutor<Entity> {

        override suspend fun execute(action: suspend (PageRequest) -> Flow<PageResult<Entity>>): Flow<Page<Entity>> {
            return action(pageRequest)
                .map { result -> result.mergeToPage(previousPage) }
                .onEach { page ->
                    Timber.d("Result after merge: offset: ${page.offset};  total:${page.total}; count:${page.entities.size}, isLastPage:${page.isLastPage}")
                }
        }

        private fun PageResult<Entity>.mergeToPage(previousPage: Page<Entity>?): Page<Entity> = let { result ->
            Timber.d("Result before merge: offset: ${offset};  total:${total}; count:${previousPage?.entities?.size}")
            previousPage?.entities?.toMutableList()
                ?.also { oldEntities -> oldEntities.addAll(result.entities) }
                ?.let { newEntities -> result.page(newEntities) }
                ?: result.page(entities = result.entities)
        }

        private fun <Entity> PageResult<Entity>.page(entities: List<Entity>): Page<Entity> = OneSidePaginationPage(
            offset = offset,
            total = total,
            isLastPage = entities.size >= total,
            entities = entities
        )
    }

    private class OneSidePaginationPage<Entity>(override val offset: Long, override val total: Long, override val entities: List<Entity>, override val isLastPage: Boolean) : Page<Entity>
}
