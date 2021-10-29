package com.pavlo.fedor.compose.domain.storage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page

interface LaunchesPageStorage : ReadableStorage<Page<LaunchInfo>>, WritableStorage<Page<LaunchInfo>>, LaunchInfoReadableStorage {
    fun replace(launchInfo: LaunchInfo)
}
