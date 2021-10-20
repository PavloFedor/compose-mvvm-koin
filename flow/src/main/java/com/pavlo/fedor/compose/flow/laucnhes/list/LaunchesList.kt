package com.pavlo.fedor.compose.flow.laucnhes.list

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState
import com.pavlo.fedor.compose.ui.widget.LoaderCell
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun LaunchesList(
    state: LaunchesListState,
    onItemClick: (LaunchInfo) -> Unit,
    onFavoriteClick: (LaunchInfo) -> Unit,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.isDataLoading),
        onRefresh = onRefresh,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

        val listState = rememberLazyListState()
        OnScroll(listState = listState, items = state.items, onLoadMore = onLoadMore)

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(PaddingValues(start = 8.dp, end = 8.dp)),
            contentPadding = PaddingValues(top = 16.dp),
            state = listState
        ) {
            items(
                items = state.items,
                itemContent = { item ->
                    when (item) {
                        is LaunchesListItemState.InfoItem -> LaunchInfoCell(item.info, onItemClick = { onItemClick(item.info) }, onFavoriteClick = { onFavoriteClick(item.info) })
                        else -> LoaderCell()
                    }
                }
            )
        }
    }
}

@Composable
private fun OnScroll(listState: LazyListState, items: List<LaunchesListItemState>, onLoadMore: () -> Unit) = LaunchedEffect(listState) {
    snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
        .distinctUntilChanged()
        .collect { index ->
            if (index >= items.lastIndex && items.getOrNull(index) !is LaunchesListItemState.Progress) {
                onLoadMore()
            }
        }
}