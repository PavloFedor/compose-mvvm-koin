package com.pavlo.fedor.compose.flow.laucnhes.list.favorite.state

import com.pavlo.fedor.compose.flow.base.state.InitialStateFactory

internal class FavoriteLaunchesInitialStateFactory : InitialStateFactory<FavoriteLaunchesState> {

    override fun invoke(): FavoriteLaunchesState = FavoriteLaunchesState(
        items = listOf(),
        isDataLoading = true,
        canLoadMore = false
    )
}
