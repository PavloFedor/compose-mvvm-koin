package com.pavlo.fedor.compose.flow.laucnhes.list.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pavlo.fedor.compose.flow.R
import com.pavlo.fedor.compose.flow.base.*
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesList
import com.pavlo.fedor.compose.ui.widget.LoaderCell
import com.pavlo.fedor.compose.ui.widget.Search
import java.util.*

object HistoryLaunchesListScreen : Screen<Unit>(parentRoute = "launches", route = "history", argsType = Argument.NotingType) {

    @Composable
    override fun Content(args: Unit, scopeId: String, parentNavController: NavController) = KoinScope(scopeId = UUID.randomUUID().toString(), qualifier = typed(HistoryLaunchesListScreen::class)) {
        Layout(viewModel = scopedViewModel(), focusManager = LocalFocusManager.current)
    }

    @Composable
    private fun Layout(viewModel: HistoryLaunchesListViewModel, focusManager: FocusManager) = Column(
        Modifier.fillMaxWidth()
            .fillMaxHeight()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {

        val state by viewModel.stateFlow.collectAsState()

        Search(text = state.searchText, onSearchChanged = viewModel::onSearchChanged)

        when {
            state.shouldShowEmptyView -> EmptyView()
            state.isDataLoading && state.items.isEmpty() -> LoaderCell(true)
            else -> LaunchesList(
                state = state,
                onItemClick = { },
                onFavoriteClick = { item -> viewModel.onFavorite(item) },
                onLoadMore = { viewModel.onListScrolledToBottom() },
                onRefresh = { viewModel.onRefresh() }
            )
        }
    }

    @Composable
    private fun EmptyView() = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
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
