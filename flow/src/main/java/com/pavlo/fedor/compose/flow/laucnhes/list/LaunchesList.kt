package com.pavlo.fedor.compose.flow.laucnhes.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.flow.R
import com.pavlo.fedor.compose.flow.laucnhes.list.history.HistoryLaunchesListScreen
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState.InfoItem
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState
import com.pavlo.fedor.compose.ui.widget.LoaderCell
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber


@Composable
fun LaunchesList(
    state: LaunchesListState,
    onItemClick: (LaunchInfo) -> Unit,
    onFavoriteClick: (LaunchInfo) -> Unit,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit
) = if (state.isDataLoading && state.items.isEmpty()) LoaderCell(true) else SwipeRefresh(
    state = rememberSwipeRefreshState(isRefreshing = state.isDataLoading),
    onRefresh = onRefresh,
    modifier = Modifier.fillMaxHeight().fillMaxWidth()
) {
    Timber.d("StateItems: ${state.items.lastOrNull()}")
    when {
        state.shouldShowEmptyView -> EmptyView()
        else -> List(
            state = state,
            onLoadMore = onLoadMore,
            onItemClick = onItemClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}


@Composable
private fun List(state: LaunchesListState, onLoadMore: () -> Unit, onItemClick: (LaunchInfo) -> Unit, onFavoriteClick: (LaunchInfo) -> Unit) {
    val listState = rememberLazyListState()
    OnScroll(listState = listState, onLoadMore = onLoadMore)

    LazyColumn(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(PaddingValues(start = 8.dp, end = 8.dp)),
        contentPadding = PaddingValues(top = 16.dp),
        state = listState
    ) {
        items(
            items = state.items.apply { Timber.d("LastItem: ${state.items.lastOrNull()}") },
            itemContent = { item ->
                Timber.d("OnItem: ${item}")
                when (item) {
                    is InfoItem -> LaunchInfoCell(item.info, onItemClick = { onItemClick(item.info) }, onFavoriteClick = { onFavoriteClick(item.info) })
                    else -> LoaderCell()
                }
            }
        )
    }
}

@Composable
private fun OnScroll(listState: LazyListState, onLoadMore: () -> Unit) = LaunchedEffect(listState) {
    snapshotFlow { (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) to listState.layoutInfo.totalItemsCount }
        .distinctUntilChanged()
        .collect { (index, count) -> if (index >= count - 1) onLoadMore() }
}

@Composable
private fun EmptyView() = LazyColumn(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
    state = rememberLazyListState(),
) {
    item {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_state),
            contentDescription = "emptyView",
            contentScale = ContentScale.Inside,
        )
        Text(
            text = "No results found",
            color = Color(0xFFA6A6A6),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}