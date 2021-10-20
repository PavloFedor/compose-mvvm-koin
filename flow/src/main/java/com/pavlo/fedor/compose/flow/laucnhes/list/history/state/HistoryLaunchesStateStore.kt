package com.pavlo.fedor.compose.flow.laucnhes.list.history.state

import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.LaunchesListStateAction
import com.pavlo.fedor.compose.flow.laucnhes.list.state.store.LaunchesLisStateStore

class HistoryLaunchesStateStore(
    initialStateFactory: HistoryLaunchesInitialStateFactory
) : LaunchesLisStateStore<HistoryLaunchesState, HistoryLaunchesMutableState>(initialStateFactory) {

    override fun HistoryLaunchesMutableState.onOtherAction(action: LaunchesListStateAction): HistoryLaunchesMutableState {
        return when (action) {
            is OnSearchTextChangedAction -> onSearchChanged(action.newSearchText)
            else -> this
        }
    }

    override fun HistoryLaunchesMutableState.onPageChanged(offset: Int, total: Int, count: Int, items: List<LaunchesListItemState>) = copy(
        offset = offset,
        total = total,
        count = count,
        items = items,
        isDataLoading = false
    )

    override fun HistoryLaunchesMutableState.onItemChanged(index: Int, updatedItem: LaunchesListItemState) = copy(
        items = items.toMutableList().also { it[index] = updatedItem }
    )

    override fun HistoryLaunchesMutableState.updateItems(items: List<LaunchesListItemState>) = copy(
        items = items,
        isDataLoading = false
    )

    override fun HistoryLaunchesMutableState.onDataLoadingStateChanged(isLoading: Boolean) = copy(
        isDataLoading = isLoading
    )

    private fun HistoryLaunchesMutableState.onSearchChanged(newQuery: String) = copy(
        searchText = newQuery
    )
}