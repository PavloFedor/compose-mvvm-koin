package com.pavlo.fedor.compose.domain

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.storage.FavoriteLaunchesPageStorage
import com.pavlo.fedor.compose.domain.storage.LaunchSingleItemStorage
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import com.pavlo.fedor.compose.domain.storage.base.ObservableStorage
import com.pavlo.fedor.compose.domain.usecase.*
import com.pavlo.fedor.compose.domain.usecase.FetchRocketLaunchesHistoryUseCase
import org.koin.dsl.module
import kotlin.math.sin

val DomainModule = module {
    factory {
        FetchRocketLaunchesHistoryUseCase(
            rocketLaunchService = get(),
            launchesPageStorage = get(),
            paginationService = get(),
            rocketLaunchDbService = get()
        )
    }

    factory { (storage: ObservableStorage<Page<LaunchInfo>>) -> OnLaunchesPageChangeUseCase(launchesPageStorage = storage) }

    factory { (storage: LaunchSingleItemStorage) ->
        ToggleFavoriteStateUseCase(dbService = get(), launchesStorage = storage)
    }
    factory {
        FetchFavoriteLaunchesUseCase(
            launchesPageStorage = get<FavoriteLaunchesPageStorage>(),
            paginationService = get(),
            dbService = get()
        )
    }
}
