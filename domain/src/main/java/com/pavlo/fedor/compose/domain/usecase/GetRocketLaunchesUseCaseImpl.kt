package com.pavlo.fedor.compose.domain.usecase

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.service.PaginationService
import com.pavlo.fedor.compose.domain.service.RocketApiLaunchService
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

internal class GetRocketLaunchesUseCaseImpl(
    private val paginationService: PaginationService,
    private val rocketLaunchService: RocketApiLaunchService,
    private val launchesPageStorage: LaunchesPageStorage
) : GetRocketLaunchesUseCase {

    override suspend fun invoke(params: GetRocketLaunchesUseCase.Params): Flow<Page<LaunchInfo>> = paginationService.onPageRequest(launchesPageStorage.takeIf { !params.refresh }?.get())
        .execute { pageRequest -> rocketLaunchService.get(query = params.query, pageRequest) }
        .onEach { newPage -> launchesPageStorage.set(newPage) }
}
