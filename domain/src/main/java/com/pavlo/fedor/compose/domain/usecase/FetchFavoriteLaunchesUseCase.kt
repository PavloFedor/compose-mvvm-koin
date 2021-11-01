package com.pavlo.fedor.compose.domain.usecase

import com.pavlo.fedor.compose.domain.service.PaginationService
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class FetchFavoriteLaunchesUseCase(
    private val launchesPageStorage: LaunchesPageStorage,
    private val paginationService: PaginationService,
    private val dbService: RocketLaunchDbService
) : FetchRocketLaunchesUseCase {
    override suspend fun invoke(params: FetchRocketLaunchesUseCase.Params): Flow<Unit> {
        return launchesPageStorage.get()
            .map { page -> page.takeIf { !params.refresh } }
            .map { page -> paginationService.onPageRequest(page) }
            .flatMapConcat { executor -> executor.execute { pageRequest -> dbService.getFavorite(pageRequest) } }
            .flatMapConcat { newPage -> launchesPageStorage.set(newPage) }
    }
}
