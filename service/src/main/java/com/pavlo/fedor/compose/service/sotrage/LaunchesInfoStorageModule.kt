package com.pavlo.fedor.compose.service.sotrage

import com.pavlo.fedor.compose.domain.storage.LaunchInfoReadableStorage
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import org.koin.core.module.Module
import org.koin.dsl.bind

internal val LaunchesInfoStorageModule: Module.() -> Unit = {
    single<LaunchesPageStorage> { LaunchesInMemoryStorage() } bind LaunchInfoReadableStorage::class
}
