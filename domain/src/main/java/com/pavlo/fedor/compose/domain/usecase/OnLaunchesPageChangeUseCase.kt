package com.pavlo.fedor.compose.domain.usecase

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import com.pavlo.fedor.compose.domain.storage.base.ObservableStorage
import kotlinx.coroutines.flow.Flow

class OnLaunchesPageChangeUseCase(private val launchesPageStorage: ObservableStorage<Page<LaunchInfo>>) : FlowUseCase<Unit, Page<LaunchInfo>> {
    override suspend fun invoke(params: Unit): Flow<Page<LaunchInfo>> = launchesPageStorage.observe()
}