package com.pavlo.fedor.compose.flow

import com.pavlo.fedor.compose.flow.base.ArgsProvider
import com.pavlo.fedor.compose.flow.base.typed
import com.pavlo.fedor.compose.flow.laucnhes.LaunchesModule
import com.pavlo.fedor.compose.flow.laucnhes.details.LaunchDetailsModule
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.FavoriteLaunchesListModule
import org.koin.dsl.module

val FLowModule = module {
    scope(typed(ArgsProvider::class)) {
        scoped { params -> ArgsProvider(params) }
    }

    LaunchesModule(this)
}
