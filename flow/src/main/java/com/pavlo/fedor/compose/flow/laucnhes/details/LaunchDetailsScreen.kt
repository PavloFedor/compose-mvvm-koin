package com.pavlo.fedor.compose.flow.laucnhes.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.pavlo.fedor.compose.flow.base.*


object LaunchDetailsScreen : Screen<String>(parentRoute = "launches", route = "details", argsType = Argument.StringArgument(key = "scopeId")) {

    @Composable
    override fun Content(args: String, scopeId: String, parentNavController: NavController) = KoinScope(scopeId = scopeId, argsScopeId = args, qualifier = typed(LaunchDetailsScreen::class)) {
        val viewModel: LaunchDetailsViewModel = scopedViewModel {
            args(args)
        }
        val state by viewModel.stateFlow.collectAsState()
        Text(text = state)
    }
}
