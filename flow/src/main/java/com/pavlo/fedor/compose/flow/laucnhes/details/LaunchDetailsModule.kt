package com.pavlo.fedor.compose.flow.laucnhes.details

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.storage.FavoriteLaunchesPageStorage
import com.pavlo.fedor.compose.domain.storage.LaunchesPageStorage
import com.pavlo.fedor.compose.flow.base.typed
import com.pavlo.fedor.compose.flow.laucnhes.details.state.LaunchInfoDetailsInitialStateFactory
import com.pavlo.fedor.compose.flow.laucnhes.details.state.LaunchInfoDetailsStateStore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

val LaunchDetailsModule: Module.() -> Unit = {
    detailsScope(typed(DetailArgs.History::class)) { args ->
        LaunchDetailsViewModelImpl(
            stateStore = get { parametersOf(args.launchInfo) },
            toggleFavoriteStateUseCase = get { parametersOf(get<LaunchesPageStorage>()) }
        )
    }
    detailsScope(typed(DetailArgs.Favourite::class)) { args ->
        LaunchDetailsViewModelImpl(
            stateStore = get { parametersOf(args.launchInfo) },
            toggleFavoriteStateUseCase = get { parametersOf(get<FavoriteLaunchesPageStorage>()) }
        )
    }
}

private fun Module.detailsScope(qualifier: Qualifier, viewModelDefinition: Scope.(DetailArgs) -> LaunchDetailsViewModel) = scope(qualifier) {
    scoped { (launchInfo: LaunchInfo) -> LaunchInfoDetailsInitialStateFactory(launchInfo) }
    scoped { (launchInfo: LaunchInfo) -> LaunchInfoDetailsStateStore(initialStateFactory = get { parametersOf(launchInfo) }) }
    viewModel { (args: DetailArgs) ->
        viewModelDefinition(this, args)
    }
}


