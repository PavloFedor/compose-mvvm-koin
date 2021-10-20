package com.pavlo.fedor.compose.flow.laucnhes.list

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.flow.base.BaseViewModel
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState

abstract class LaunchesListViewModel<State : LaunchesListState> : BaseViewModel<State>() {

    abstract fun onListScrolledToBottom()

    abstract fun onRefresh()

    abstract fun onFavorite(launchInfo: LaunchInfo)
}

