package com.pavlo.fedor.compose.flow.laucnhes.list.favorite

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pavlo.fedor.compose.flow.base.Argument
import com.pavlo.fedor.compose.flow.base.Screen

object FavoriteLaunchesListScreen : Screen<Unit>(parentRoute = "launches", route = "favorite", argsType = Argument.NotingType) {

    @Composable
    override fun Content(args: Unit, scopeId: String, parentNavController: NavController) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            MaterialTheme.colors.background,
            darkIcons = true
        )

        Text(text = "LaunchesHistoryFavorite")
    }
}