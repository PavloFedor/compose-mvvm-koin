package com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state

import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState

internal data class FavoriteLaunchesState(
    override val items: List<LaunchesListItemState>,
    override val isDataLoading: Boolean,
    override val canLoadMore: Boolean
) : LaunchesListState {
    override val shouldShowEmptyView: Boolean get() = !isDataLoading && items.isEmpty()
    override val isLoadingMore: Boolean get() = items.lastOrNull() is LaunchesListItemState.Progress
}
