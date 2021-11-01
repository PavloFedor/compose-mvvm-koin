package com.pavlo.fedor.compose.flow

import com.pavlo.fedor.compose.flow.laucnhes.LaunchesModule
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.FavoriteLaunchesListModule
import org.koin.dsl.module

val FLowModule = module {
    LaunchesModule(this)
}
