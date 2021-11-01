package com.pavlo.fedor.compose.domain.storage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.storage.base.ObservableStorage
import com.pavlo.fedor.compose.domain.storage.base.ReadableStorage
import com.pavlo.fedor.compose.domain.storage.base.WritableStorage
import kotlinx.coroutines.flow.Flow

interface LaunchesPageStorage : ObservableStorage<Page<LaunchInfo>>, ReadableStorage<Flow<Page<LaunchInfo>?>>, WritableStorage<Page<LaunchInfo>, Flow<Unit>>, LaunchSingleItemStorage
