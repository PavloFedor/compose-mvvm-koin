package com.pavlo.fedor.compose.flow.laucnhes.list.state


interface LaunchesListState {
    val items: List<LaunchesListItemState>

    val offset: Int
    val total: Int
    val count: Int

    val isDataLoading: Boolean

    val shouldShowEmptyView: Boolean
}
