package com.pavlo.fedor.compose.flow.laucnhes.list.state


interface LaunchesListState {
    val items: List<LaunchesListItemState>
    val isDataLoading: Boolean
    val shouldShowEmptyView: Boolean
    val canLoadMore: Boolean
    val isLoadingMore: Boolean
}
