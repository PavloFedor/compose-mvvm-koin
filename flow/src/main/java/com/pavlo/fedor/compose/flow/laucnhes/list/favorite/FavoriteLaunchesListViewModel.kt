package com.pavlo.fedor.compose.flow.laucnhes.list.favorite

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.usecase.*
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesListViewModel
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state.FavoriteLaunchesStateStore
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnDataLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnNewPageLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnPageChanged
import kotlinx.coroutines.flow.*

internal class FavoriteLaunchesListViewModel(
    private val stateStore: FavoriteLaunchesStateStore,
    private val fetchLaunchesUseCase: FetchRocketLaunchesUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
    private val onLaunchesPageChangeUseCase: OnLaunchesPageChangeUseCase
) : LaunchesListViewModel<LaunchesListState>() {

    override val stateFlow: StateFlow<LaunchesListState> get() = stateStore.state

    init {
        launch {
            onLaunchesPageChangeUseCase(Unit).collect { newPage ->
                stateStore.dispatch(OnPageChanged(newPage))
            }
        }

        onRefresh()
    }

    override fun onListScrolledToBottom() = launch {
        flowOf(stateFlow.value).filter { state -> state.canLoadMore && !state.isDataLoading && !state.isLoadingMore }
            .map { FetchRocketLaunchesUseCase.Params(query = null, refresh = false) }
            .flatMapConcat { params -> fetchLaunchesUseCase(params).onStart { stateStore.dispatch(OnNewPageLoadingChanged(isLoading = true)) } }
            .handleError()
            .collect()
    }

    override fun onRefresh() = launch {
        fetchLaunchesUseCase(FetchRocketLaunchesUseCase.Params(query = null, refresh = true))
            .onStart { stateStore.dispatch(OnDataLoadingChanged(true)) }
            .handleError { stateStore.dispatch(OnDataLoadingChanged(false)) }
            .collect()
    }

    override fun onFavorite(launchInfo: LaunchInfo) = launch {
        toggleFavoriteStateUseCase(launchInfo)
            .handleError()
            .collect()
    }
}
