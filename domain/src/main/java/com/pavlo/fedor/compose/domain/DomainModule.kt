package com.pavlo.fedor.compose.domain

import com.pavlo.fedor.compose.domain.usecase.GetRocketLaunchesUseCase
import com.pavlo.fedor.compose.domain.usecase.GetRocketLaunchesUseCaseImpl
import org.koin.dsl.module

val DomainModule = module {
    single<GetRocketLaunchesUseCase> {
        GetRocketLaunchesUseCaseImpl(
            rocketLaunchService = get(),
            launchesPageStorage = get(),
            paginationService = get()
        )
    }
}