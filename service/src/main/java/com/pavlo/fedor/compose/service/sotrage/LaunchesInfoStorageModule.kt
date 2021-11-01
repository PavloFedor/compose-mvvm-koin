package com.pavlo.fedor.compose.service.sotrage

import com.pavlo.fedor.compose.domain.storage.FavoriteLaunchesPageStorage
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import org.koin.core.module.Module

internal val LaunchesInfoStorageModule: Module.() -> Unit = {
    single<LaunchesPageStorage> { LaunchesHistoryInMemoryStorage() }
    single<FavoriteLaunchesPageStorage> { LaunchesFavoriteInMemoryStorage(launchesPageStorage = get()) }
}
