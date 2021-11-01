package com.pavlo.fedor.compose.flow.laucnhes.list.history

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import com.pavlo.fedor.compose.flow.base.*
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesList
import com.pavlo.fedor.compose.ui.widget.Search
import timber.log.Timber
import java.util.*

object HistoryLaunchesListScreen : Screen<Unit>(parentRoute = "launches", route = "history", argsType = Argument.NotingType) {

    @Composable
    override fun Content(args: Unit, scopeId: String, parentNavController: NavController) = KoinScope(scopeId = scopeId, qualifier = typed(HistoryLaunchesListScreen::class)) {
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

        LaunchesList(
            state = state,
            onItemClick = { Timber.d("${it.countryFlagLink}") },
            onFavoriteClick = { item -> viewModel.onFavorite(item) },
            onLoadMore = { viewModel.onListScrolledToBottom() },
            onRefresh = { viewModel.onRefresh() }
        )
    }
}
