package com.pavlo.fedor.compose.flow.laucnhes.list.history.state

import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.LaunchesListStateAction
import com.pavlo.fedor.compose.flow.laucnhes.list.state.store.LaunchesListStateStore
import timber.log.Timber

class HistoryLaunchesStateStore(
    initialStateFactory: HistoryLaunchesInitialStateFactory
) : LaunchesListStateStore<HistoryLaunchesState, HistoryLaunchesMutableState>(initialStateFactory) {

    override fun HistoryLaunchesMutableState.onOtherAction(action: LaunchesListStateAction): HistoryLaunchesMutableState {
        return when (action) {
            is OnSearchTextChangedAction -> onSearchChanged(action.newSearchText)
            else -> this
        }
    }

    override fun HistoryLaunchesMutableState.onPageChanged(isLastPage: Boolean, items: List<LaunchesListItemState>) = copy(
        items = items,
        isDataLoading = false,
        canLoadMore = isLastPage.not().apply { Timber.d("HistoryLaunchesMutableState.isLastPage: $isLastPage; ${items.lastOrNull()}") }
    )

    override fun HistoryLaunchesMutableState.updateItems(items: List<LaunchesListItemState>) = copy(
        items = items,
        isDataLoading = false
    )

    override fun HistoryLaunchesMutableState.onDataLoadingStateChanged(isLoading: Boolean) = copy(
        isDataLoading = isLoading.apply { Timber.d("onDataLoadingStateChanged: isLoading: $isLoading; canLoadMore:$canLoadMore; ${items.lastOrNull()}") }
    )

    private fun HistoryLaunchesMutableState.onSearchChanged(newQuery: String) = copy(
        searchText = newQuery
    )
}