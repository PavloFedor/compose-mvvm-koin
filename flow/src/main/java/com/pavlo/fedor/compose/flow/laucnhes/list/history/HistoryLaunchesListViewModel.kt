package com.pavlo.fedor.compose.flow.laucnhes.list.history

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.usecase.GetRocketLaunchesUseCase
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesListViewModel
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.HistoryLaunchesState
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.HistoryLaunchesStateStore
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.OnSearchTextChangedAction
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnDataLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnItemChange
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnNewPageLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnPageChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.lang.IllegalStateException

class HistoryLaunchesListViewModel(
    private val stateStore: HistoryLaunchesStateStore,
    private val getRocketLaunchesUseCase: GetRocketLaunchesUseCase
) : LaunchesListViewModel<HistoryLaunchesState>() {

    override val stateFlow: StateFlow<HistoryLaunchesState> get() = stateStore.state

    init {
        launch {
            flowOf(stateFlow.value).map { state -> GetRocketLaunchesUseCase.Params(state.searchText, refresh = true) }
                .flatMapConcat { params -> getRocketLaunchesUseCase(params = params) }
                .handleError()
                .collect { page -> stateStore.dispatch(OnPageChanged(page)) }
        }
    }

    override fun onListScrolledToBottom() = launch {
        flowOf(stateFlow.value).filter { state -> state.canLoadMore && !state.isDataLoading }
            .onStart { stateStore.dispatch(OnNewPageLoadingChanged(isLoading = true)) }
            .map { state -> GetRocketLaunchesUseCase.Params(state.searchText, refresh = false) }
            .flatMapConcat { params -> getRocketLaunchesUseCase(params) }
            .handleError()
            .collect { page -> stateStore.dispatch(OnPageChanged(newPage = page)) }
    }

    override fun onRefresh() = launch {
        flowOf(stateFlow.value).map { state -> GetRocketLaunchesUseCase.Params(state.searchText, refresh = true) }
            .onStart { stateStore.dispatch(OnDataLoadingChanged(true)) }
            .flatMapConcat { params -> getRocketLaunchesUseCase(params = params) }
            .handleError()
            .collect { page -> stateStore.dispatch(OnPageChanged(page)) }
    }


    fun onSearchChanged(query: String) = launch {
        flowOf(query)
            .onEach { stateStore.dispatch(OnSearchTextChangedAction(it)) }
            .debounce(150)
            .onEach { stateStore.dispatch(OnDataLoadingChanged(isLoading = true)) }
            .map { GetRocketLaunchesUseCase.Params(query = query.takeIf { it.isNotEmpty() }, refresh = true) }
            .flatMapConcat { params -> getRocketLaunchesUseCase(params = params) }
            .handleError()
            .collect { page -> stateStore.dispatch(OnPageChanged(page)) }
    }

    override fun onFavorite(launchInfo: LaunchInfo) = launch {
        /*  val newItem = launchInfo.copy(isFavorite = !launchInfo.isFavorite)
          stateStore.dispatch(OnItemChange(newItem))*/
    }

    private suspend fun <T> Flow<T>.handleError(): Flow<T> = catch { error ->
        Timber.e(error)
        stateStore.dispatch(OnDataLoadingChanged(isLoading = false))
    }
}
