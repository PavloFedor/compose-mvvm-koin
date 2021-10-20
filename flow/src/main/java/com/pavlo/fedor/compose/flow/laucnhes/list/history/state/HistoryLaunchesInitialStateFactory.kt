package com.pavlo.fedor.compose.flow.laucnhes.list.history.state

import com.pavlo.fedor.compose.flow.base.state.InitialStateFactory

class HistoryLaunchesInitialStateFactory : InitialStateFactory<HistoryLaunchesMutableState>{

    override fun invoke(): HistoryLaunchesMutableState {
        return HistoryLaunchesMutableState(
            searchText = "",
            isDataLoading = true,
            offset = 0,
            total = 0,
            count = 0,
            items = listOf()
        )
    }
}