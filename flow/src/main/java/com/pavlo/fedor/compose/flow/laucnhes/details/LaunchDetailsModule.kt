package com.pavlo.fedor.compose.flow.laucnhes.details

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.flow.base.typed
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

val LaunchDetailsModule: Module.() -> Unit = {
    scope(typed(LaunchDetailsScreen::class)) {

        viewModel { (launchInfo: LaunchInfo) ->
            LaunchDetailsViewModel(launchInfo)
        }
    }
}