package com.pavlo.fedor.compose.flow.laucnhes.list.favorite

import com.pavlo.fedor.compose.domain.storage.FavoriteLaunchesPageStorage
import com.pavlo.fedor.compose.domain.usecase.FetchFavoriteLaunchesUseCase
import com.pavlo.fedor.compose.flow.base.typed
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state.FavoriteLaunchesInitialStateFactory
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state.FavoriteLaunchesStateStore
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf

val FavoriteLaunchesListModule: Module.() -> Unit = {
    scope(typed(FavoriteLaunchesListScreen::class)) {
        scoped { FavoriteLaunchesInitialStateFactory() }
        scoped { FavoriteLaunchesStateStore(initialStateFactory = get()) }

        scoped {
            FavoriteLaunchesListViewModel(
                stateStore = get(),
                fetchLaunchesUseCase = get<FetchFavoriteLaunchesUseCase>(),
                toggleFavoriteStateUseCase = get { parametersOf(get<FavoriteLaunchesPageStorage>()) },
                onLaunchesPageChangeUseCase = get { parametersOf(get<FavoriteLaunchesPageStorage>()) }
            )
        }
    }
}
