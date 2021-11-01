package com.pavlo.fedor.compose.domain.storage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.storage.base.ReplaceableStorage
import com.pavlo.fedor.compose.domain.storage.base.SelectableStorage
import kotlinx.coroutines.flow.Flow

interface LaunchSingleItemStorage : ReplaceableStorage<LaunchInfo, Flow<Unit>>, SelectableStorage<LaunchInfoIdFilter, Flow<LaunchInfo?>>