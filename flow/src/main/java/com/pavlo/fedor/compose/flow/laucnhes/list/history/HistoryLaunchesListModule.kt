package com.pavlo.fedor.compose.flow.laucnhes.list.history

import com.pavlo.fedor.compose.flow.base.typed
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.HistoryLaunchesInitialStateFactory
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.HistoryLaunchesStateStore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

internal val HistoryLaunchesListModule: Module.() -> Unit = {

    scope(typed(HistoryLaunchesListScreen::class)) {
        scoped { HistoryLaunchesInitialStateFactory() }
        scoped { HistoryLaunchesStateStore(initialStateFactory = get()) }
        viewModel {
            HistoryLaunchesListViewModel(stateStore = get())
        }
    }
}
