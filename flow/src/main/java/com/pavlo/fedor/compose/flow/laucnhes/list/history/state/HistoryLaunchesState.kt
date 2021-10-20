package com.pavlo.fedor.compose.flow.laucnhes.list.history.state

import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState

interface HistoryLaunchesState : LaunchesListState {
    val searchText: String
}