package com.pavlo.fedor.compose.domain.usecase

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService
import com.pavlo.fedor.compose.domain.storage.LaunchSingleItemStorage
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ToggleFavoriteStateUseCase(
    private val dbService: RocketLaunchDbService,
    private val launchesStorage: LaunchSingleItemStorage
) : FlowUseCase<LaunchInfo, Unit> {
    override suspend fun invoke(params: LaunchInfo): Flow<Unit> {
        return flowOf(params.copy(isFavorite = !params.isFavorite))
            .flatMapConcat { item -> dbService.set(item).map { item } }
            .flatMapConcat { item -> launchesStorage.replace(value = item) }
    }
}
