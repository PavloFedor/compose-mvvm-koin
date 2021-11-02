package com.pavlo.fedor.compose.flow.laucnhes.list.favorite

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pavlo.fedor.compose.flow.base.*
import com.pavlo.fedor.compose.flow.laucnhes.details.DetailArgs
import com.pavlo.fedor.compose.flow.laucnhes.details.LaunchDetailsScreen
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesList
import java.util.*

object FavoriteLaunchesListScreen : Screen<Unit>(parentRoute = "launches", route = "favorite", argsType = Argument.NotingType) {

    @Composable
    override fun Content(args: Unit, scopeId: String, parentNavController: NavController) = KoinScope(scopeId = scopeId, qualifier = typed(FavoriteLaunchesListScreen::class)) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            MaterialTheme.colors.background,
            darkIcons = true
        )
        Layout(viewModel = scopedViewModel(), navController = parentNavController)
    }


    @Composable
    private fun Layout(viewModel: FavoriteLaunchesListViewModel, navController: NavController) {
        val state by viewModel.stateFlow.collectAsState()
        LaunchesList(
            state = state,
            onItemClick = { item -> navController.navigate(LaunchDetailsScreen, DetailArgs.Favourite(item)) },
            onFavoriteClick = viewModel::onFavorite,
            onLoadMore = viewModel::onListScrolledToBottom,
            onRefresh = viewModel::onRefresh
        )
    }
}