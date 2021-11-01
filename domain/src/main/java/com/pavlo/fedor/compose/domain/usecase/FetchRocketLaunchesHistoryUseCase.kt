package com.pavlo.fedor.compose.domain.usecase

import com.pavlo.fedor.compose.domain.service.PaginationService
import com.pavlo.fedor.compose.domain.service.RocketApiLaunchService
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import kotlinx.coroutines.flow.*

class FetchRocketLaunchesHistoryUseCase(
    private val paginationService: PaginationService,
    private val rocketLaunchService: RocketApiLaunchService,
    private val rocketLaunchDbService: RocketLaunchDbService,
    private val launchesPageStorage: LaunchesPageStorage
) : FetchRocketLaunchesUseCase {

    override suspend fun invoke(params: FetchRocketLaunchesUseCase.Params): Flow<Unit> = launchesPageStorage.get()
        .map { lastPage -> paginationService.onPageRequest(lastPage?.takeIf { !params.refresh }) }
        .flatMapConcat { executor -> executor.execute { request -> rocketLaunchService.get(params.query, request) } }
        .flatMapConcat { newPage -> launchesPageStorage.set(newPage).map { newPage } }
        .flatMapConcat { newPage -> rocketLaunchDbService.set(newPage.entities) }
        .onErrorFallBack(params)

    private fun <T> Flow<T>.onErrorFallBack(params: FetchRocketLaunchesUseCase.Params) = catch { error ->
        launchesPageStorage.get()
            .map { page -> page.takeIf { !params.refresh } }
            .map { lastPage -> paginationService.onPageRequest(lastPage) }
            .flatMapConcat { executor -> executor.execute { pageRequest -> rocketLaunchDbService.get(params.query, pageRequest) } }
            .flatMapConcat { fallbackPage -> launchesPageStorage.set(fallbackPage) }
            .map { error }
            .collect { throw it }
    }
}
