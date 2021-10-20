package com.pavlo.fedor.compose.flow.laucnhes

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pavlo.fedor.compose.flow.base.*
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.FavoriteLaunchesListScreen
import com.pavlo.fedor.compose.flow.laucnhes.list.history.HistoryLaunchesListScreen
import com.pavlo.fedor.compose.flow.laucnhes.state.LaunchesNavigationState
import com.pavlo.fedor.compose.ui.widget.BottomBar
import com.pavlo.fedor.compose.ui.widget.NavigationCell

object LaunchesNavigationScreen : Screen<Unit>(route = "launches", Argument.NotingType) {


    @Composable
    override fun Content(args: Unit, scopeId: String, parentNavController: NavController) = KoinScope(scopeId = scopeId, qualifier = typed(LaunchesNavigationScreen::class)) {
        val viewModel: LaunchesNavigationViewModel = scopedViewModel()
        val navController = rememberNavController()
        val state by viewModel.stateFlow.collectAsState()

        navController.currentBackStackEntryAsState()
        NavigationContent(state, parentNavController, navController)
        DisposableEffect(key1 = navController) {
            val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
                controller.currentBackStackEntry?.destination?.route
                    ?.let { route -> Screen.hostFrom(route) }
                    ?.also(viewModel::onSelected)
            }
            navController.addOnDestinationChangedListener(listener)
            onDispose {
                navController.removeOnDestinationChangedListener(listener)
            }
        }
    }


    @Composable
    private fun NavigationContent(
        state: LaunchesNavigationState,
        parentNavController: NavController,
        navController: NavHostController,
    ) = Scaffold(bottomBar = {
        BottomBar(state.navigationCells, state.selectedCell) { cell ->
            if (cell.id != state.selectedCell.id) {
                navController.navigate(cell)
            }
        }
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HistoryLaunchesListScreen.toString(),
            modifier = Modifier.padding(paddingValues)
        ) {
            HistoryLaunchesListScreen.registerRoute(parentNavController)(this)
            FavoriteLaunchesListScreen.registerRoute(parentNavController)(this)
        }
    }

    private fun NavController.navigate(cell: NavigationCell) {
        val route = when (cell.id) {
            FavoriteLaunchesListScreen.host -> FavoriteLaunchesListScreen.route(Unit)
            else -> HistoryLaunchesListScreen.route(Unit)
        }

        backQueue.firstOrNull { it.destination.route == route }
            ?.run { popBackStack(route = route, inclusive = false) }
            ?: navigate(route = route)
    }
}
