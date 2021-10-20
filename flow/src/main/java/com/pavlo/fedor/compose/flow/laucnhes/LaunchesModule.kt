package com.pavlo.fedor.compose.flow.laucnhes

import com.pavlo.fedor.compose.flow.base.typed
import com.pavlo.fedor.compose.flow.laucnhes.list.history.HistoryLaunchesListModule
import com.pavlo.fedor.compose.flow.laucnhes.state.LaunchesNavigationInitialStateFactory
import com.pavlo.fedor.compose.flow.laucnhes.state.LaunchesNavigationStateStore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

internal val LaunchesModule: Module.() -> Unit = {
    scope(typed(LaunchesNavigationScreen::class)) {
        scoped { LaunchesNavigationInitialStateFactory() }
        scoped { LaunchesNavigationStateStore(initialStateFactory = get()) }

        viewModel<LaunchesNavigationViewModel> {
            LaunchesNavigationViewModelImpl(stateStore = get())
        }
    }

    HistoryLaunchesListModule(this)
}
