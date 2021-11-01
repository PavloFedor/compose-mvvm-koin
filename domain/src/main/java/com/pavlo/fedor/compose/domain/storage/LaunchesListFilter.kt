package com.pavlo.fedor.compose.domain.storage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService

sealed class LaunchesListFilter : Filter<LaunchInfo> {

    object EmptyFilter : LaunchesListFilter() {
        override fun invoke(p1: LaunchInfo): Boolean = true
    }

    class SearchFilter(private val stringQuery: String) : LaunchesListFilter() {
        override fun invoke(info: LaunchInfo): Boolean = info.name.contains(stringQuery)
    }

    class FavoriteFilter(private val isFavorite: Boolean) : LaunchesListFilter() {
        override fun invoke(info: LaunchInfo): Boolean = info.isFavorite == isFavorite
    }

    class Ids(private val ids:List<RocketLaunchDbService.LaunchIds>)
}