package com.pavlo.fedor.compose.domain.usecase

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ToggleFavoriteStateUseCase(
    private val dbService: RocketLaunchDbService,
    private val launchesStorage: LaunchesPageStorage
) : FlowUseCase<LaunchInfo, LaunchInfo> {
    override suspend fun invoke(params: LaunchInfo): Flow<LaunchInfo> {
        return flowOf(params)
            .flatMapConcat { if (!it.isFavorite) dbService.set(params) else dbService.delete(params) }
            .map { params.copy(isFavorite = !params.isFavorite).apply { launchesStorage.replace(this) } }
    }
}