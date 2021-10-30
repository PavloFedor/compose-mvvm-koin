package com.pavlo.fedor.compose.domain

import com.pavlo.fedor.compose.domain.usecase.GetRocketLaunchesUseCase
import com.pavlo.fedor.compose.domain.usecase.GetAllRocketLaunchesUseCase
import com.pavlo.fedor.compose.domain.usecase.GetFavoritesRocketLaunchesUseCase
import com.pavlo.fedor.compose.domain.usecase.ToggleFavoriteStateUseCase
import org.koin.dsl.module

val DomainModule = module {
    single<GetRocketLaunchesUseCase> {
        GetAllRocketLaunchesUseCase(
            rocketLaunchService = get(),
            launchesPageStorage = get(),
            paginationService = get()
        )
    }

    single {
        GetFavoritesRocketLaunchesUseCase(dbService = get(), paginationService = get())
    }

    single {
        ToggleFavoriteStateUseCase(dbService = get(), launchesStorage = get())
    }
}
