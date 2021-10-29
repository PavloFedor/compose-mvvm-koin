package com.pavlo.fedor.compose.flow.laucnhes.list.history.state

import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState

data class HistoryLaunchesMutableState(
    override val searchText: String,
    override val items: List<LaunchesListItemState>,
    override val isDataLoading: Boolean = true,
    override val canLoadMore: Boolean
) : HistoryLaunchesState {
    override val shouldShowEmptyView: Boolean = !isDataLoading && items.isEmpty()
}