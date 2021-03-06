package com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state

import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.store.LaunchesListStateStore

internal class FavoriteLaunchesStateStore(
    initialStateFactory: FavoriteLaunchesInitialStateFactory,
) : LaunchesListStateStore<LaunchesListState, FavoriteLaunchesState>(initialStateFactory) {

    override fun FavoriteLaunchesState.onPageChanged(isLastPage: Boolean, items: List<LaunchesListItemState>): FavoriteLaunchesState {
        return copy(
            items = items,
            canLoadMore = isLastPage.not(),
            isDataLoading = false
        )
    }


    override fun FavoriteLaunchesState.updateItems(items: List<LaunchesListItemState>): FavoriteLaunchesState {
        return copy(
            items = items,
            isDataLoading = true,
        )
    }

    override fun FavoriteLaunchesState.onDataLoadingStateChanged(isLoading: Boolean): FavoriteLaunchesState {
        return copy(isDataLoading = isDataLoading)
    }
}

