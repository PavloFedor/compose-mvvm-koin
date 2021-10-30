package com.pavlo.fedor.compose.flow.laucnhes.list.favorite

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.usecase.GetFavoritesRocketLaunchesUseCase
import com.pavlo.fedor.compose.domain.usecase.GetRocketLaunchesUseCase
import com.pavlo.fedor.compose.domain.usecase.ToggleFavoriteStateUseCase
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesListViewModel
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state.FavoriteLaunchesStateStore
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnDataLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnItemChange
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnPageChanged
import kotlinx.coroutines.flow.*
import timber.log.Timber

internal class FavoriteLaunchesListViewModel(
    private val stateStore: FavoriteLaunchesStateStore,
    private val getRocketLaunchesUseCase: GetFavoritesRocketLaunchesUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase
) : LaunchesListViewModel<LaunchesListState>() {

    override val stateFlow: StateFlow<LaunchesListState> get() = stateStore.state

    init {
        onRefresh()
    }

    override fun onListScrolledToBottom() {
        Timber.d("OnScroll")
    }

    override fun onRefresh() = launch {
        getRocketLaunchesUseCase(GetRocketLaunchesUseCase.Params(query = null, refresh = true))
            .onStart { stateStore.dispatch(OnDataLoadingChanged(true)) }
            .catch {
                stateStore.dispatch(OnDataLoadingChanged(false))
                Timber.e(it)
            }
            .collect { page -> stateStore.dispatch(OnPageChanged(page)) }
    }

    override fun onFavorite(launchInfo: LaunchInfo) = launch {
        toggleFavoriteStateUseCase(launchInfo)
            .catch { Timber.e(it) }
            .collect { stateStore.dispatch(OnItemChange(it)) }
    }
}
