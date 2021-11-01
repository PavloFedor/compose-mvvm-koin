package com.pavlo.fedor.compose.flow.laucnhes.list.history

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.usecase.FetchRocketLaunchesUseCase
import com.pavlo.fedor.compose.domain.usecase.OnLaunchesPageChangeUseCase
import com.pavlo.fedor.compose.domain.usecase.ToggleFavoriteStateUseCase
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesListViewModel
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.HistoryLaunchesState
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.HistoryLaunchesStateStore
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.OnSearchTextChangedAction
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnDataLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnNewPageLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnPageChanged
import kotlinx.coroutines.flow.*

class HistoryLaunchesListViewModel(
    private val stateStore: HistoryLaunchesStateStore,
    private val fetchLaunchesUseCase: FetchRocketLaunchesUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
    private val onLaunchesPageChangeUseCase: OnLaunchesPageChangeUseCase
) : LaunchesListViewModel<HistoryLaunchesState>() {

    override val stateFlow: StateFlow<HistoryLaunchesState> get() = stateStore.state

    init {
        launch {
            onLaunchesPageChangeUseCase(Unit).collect { newPage ->
                stateStore.dispatch(OnPageChanged(newPage = newPage))
            }
        }
        onRefresh()
    }

    override fun onListScrolledToBottom() = launch {
        flowOf(stateFlow.value).filter { state -> state.canLoadMore && !state.isDataLoading && !state.isLoadingMore }
            .map { state -> FetchRocketLaunchesUseCase.Params(state.searchText, refresh = false) }
            .flatMapConcat { params -> fetchLaunchesUseCase(params).onStart { stateStore.dispatch(OnNewPageLoadingChanged(isLoading = true)) } }
            .handleError()
            .collect()
    }

    override fun onRefresh() = launch {
        flowOf(stateFlow.value).map { state -> FetchRocketLaunchesUseCase.Params(state.searchText, refresh = true) }
            .onStart { stateStore.dispatch(OnDataLoadingChanged(true)) }
            .flatMapConcat { params -> fetchLaunchesUseCase(params = params) }
            .handleError { stateStore.dispatch(OnDataLoadingChanged(isLoading = false)) }
            .collect()
    }


    fun onSearchChanged(query: String) = launch {
        flowOf(query)
            .filter { stateFlow.value.searchText != it }
            .onEach { stateStore.dispatch(OnSearchTextChangedAction(it)) }
            .debounce(150)
            .onEach { stateStore.dispatch(OnDataLoadingChanged(isLoading = true)) }
            .map { FetchRocketLaunchesUseCase.Params(query = query.takeIf { it.isNotEmpty() }, refresh = true) }
            .flatMapConcat { params -> fetchLaunchesUseCase(params = params) }
            .handleError { stateStore.dispatch(OnDataLoadingChanged(isLoading = false)) }
            .collect()
    }

    override fun onFavorite(launchInfo: LaunchInfo) = launch {
        toggleFavoriteStateUseCase(launchInfo)
            .handleError()
            .collect()
    }
}
