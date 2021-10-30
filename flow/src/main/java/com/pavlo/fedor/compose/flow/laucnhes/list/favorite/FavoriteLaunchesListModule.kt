package com.pavlo.fedor.compose.flow.laucnhes.list.favorite

import com.pavlo.fedor.compose.flow.base.typed
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state.FavoriteLaunchesInitialStateFactory
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state.FavoriteLaunchesStateStore
import org.koin.core.module.Module

val FavoriteLaunchesListModule: Module.() -> Unit = {
    scope(typed(FavoriteLaunchesListScreen::class)) {
        scoped { FavoriteLaunchesInitialStateFactory() }
        scoped { FavoriteLaunchesStateStore(initialStateFactory = get()) }

        scoped {
            FavoriteLaunchesListViewModel(
                stateStore = get(),
                getRocketLaunchesUseCase = get(),
                toggleFavoriteStateUseCase = get()
            )
        }
    }
}
