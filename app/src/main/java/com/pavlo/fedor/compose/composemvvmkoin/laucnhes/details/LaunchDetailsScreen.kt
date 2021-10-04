package com.pavlo.fedor.compose.composemvvmkoin.laucnhes.details

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pavlo.fedor.compose.composemvvmkoin.base.KoinScope
import com.pavlo.fedor.compose.composemvvmkoin.base.Screen
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.qualifier.named

class LaunchDetailsScreen : Screen("LaunchDetails") {

    @Composable
    override fun Content() {
        KoinScope(scopeId = route, qualifier = named(route)) {

        }
    }
}
