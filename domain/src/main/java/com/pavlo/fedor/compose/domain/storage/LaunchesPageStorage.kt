package com.pavlo.fedor.compose.domain.storage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import kotlinx.coroutines.flow.Flow

interface LaunchesPageStorage : ReadableStorage<Flow<Page<LaunchInfo>>>, WritableStorage<Flow<Page<LaunchInfo>>>, LaunchInfoReadableStorage {
    fun replace(launchInfo: LaunchInfo)
}
