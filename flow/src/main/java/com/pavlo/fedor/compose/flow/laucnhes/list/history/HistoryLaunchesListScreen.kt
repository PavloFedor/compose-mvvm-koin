package com.pavlo.fedor.compose.flow.laucnhes.list.history

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pavlo.fedor.compose.flow.base.*
import com.pavlo.fedor.compose.flow.laucnhes.details.LaunchDetailsScreen
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesList
import com.pavlo.fedor.compose.ui.widget.Search
import timber.log.Timber
import java.util.*

object HistoryLaunchesListScreen : Screen<Unit>(parentRoute = "launches", route = "history", argsType = Argument.NotingType) {

    @Composable
    override fun Content(args: Unit, scopeId: String, parentNavController: NavController) = KoinScope(scopeId = scopeId, qualifier = typed(HistoryLaunchesListScreen::class)) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            MaterialTheme.colors.primary,
            darkIcons = true
        )
        Layout(viewModel = scopedViewModel(), focusManager = LocalFocusManager.current, navController = parentNavController)
    }

    @Composable
    private fun Layout(navController: NavController, viewModel: HistoryLaunchesListViewModel, focusManager: FocusManager) = Column(
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
            onItemClick = { item -> navController.navigate(LaunchDetailsScreen, item) },
            onFavoriteClick = viewModel::onFavorite,
            onLoadMore = viewModel::onListScrolledToBottom,
            onRefresh = viewModel::onRefresh
        )
    }
}
