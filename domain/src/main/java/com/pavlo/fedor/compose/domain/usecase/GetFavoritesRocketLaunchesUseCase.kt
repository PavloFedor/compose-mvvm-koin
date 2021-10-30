package com.pavlo.fedor.compose.domain.usecase

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.service.PaginationService
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService
import kotlinx.coroutines.flow.Flow

class GetFavoritesRocketLaunchesUseCase(
    private val dbService: RocketLaunchDbService,
    private val paginationService: PaginationService
) : GetRocketLaunchesUseCase {

    override suspend fun invoke(params: GetRocketLaunchesUseCase.Params): Flow<Page<LaunchInfo>> {
        return paginationService.onPageRequest<LaunchInfo>(null)
            .execute { dbService.get(it) }
    }
}
