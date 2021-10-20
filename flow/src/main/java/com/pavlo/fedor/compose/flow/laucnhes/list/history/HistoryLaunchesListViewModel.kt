package com.pavlo.fedor.compose.flow.laucnhes.list.history

import android.util.Log
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesListViewModel
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.HistoryLaunchesState
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.HistoryLaunchesStateStore
import com.pavlo.fedor.compose.flow.laucnhes.list.history.state.OnSearchTextChangedAction
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnDataLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnItemChange
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnNewPageLoadingChanged
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.OnPageChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class HistoryLaunchesListViewModel(
    private val stateStore: HistoryLaunchesStateStore
) : LaunchesListViewModel<HistoryLaunchesState>() {

    override val stateFlow: StateFlow<HistoryLaunchesState> get() = stateStore.state

    init {
        launch {
            stateStore.dispatch(OnDataLoadingChanged(true))
            delay(5 * 1000)
            stateStore.dispatch(
                OnPageChanged(
                    Page(
                        entities = listOf(),
                        offset = 0,
                        total = 0
                    )
                )
            )
        }
    }
    override fun onListScrolledToBottom() = launch {
        Log.d("HistoryLaunchesList", "onListScrolledToBottom: ${stateFlow.value.items.lastOrNull()}")
        stateStore.dispatch(OnNewPageLoadingChanged(isLoading = true))
        Log.d("HistoryLaunchesList", "onListScrolledToBottom: ${stateFlow.value.items.lastOrNull()}")
        delay(5 * 1000)
        stateStore.dispatch(OnNewPageLoadingChanged(isLoading = false))
        Log.d("HistoryLaunchesList", "onListScrolledToBottom: ${stateFlow.value.items.lastOrNull()}")
    }

    override fun onRefresh() = launch {
        stateStore.dispatch(OnDataLoadingChanged(true))
        delay(5 * 1000)
        stateStore.dispatch(OnDataLoadingChanged(false))
    }

    fun onSearchChanged(query: String) = launch {
        stateStore.dispatch(OnSearchTextChangedAction(query))
    }

    override fun onFavorite(launchInfo: LaunchInfo) = launch {
        val newItem = launchInfo.copy(isFavorite = !launchInfo.isFavorite)
        stateStore.dispatch(OnItemChange(newItem))
    }
}

private val test = listOf(
    LaunchInfo(
        id = 1,
        name = "Thor DM-21 Agena-B",
        infoPage = "",
        imageUrl = "https://novosti-kosmonavtiki.ru/upload/iblock/a2c/-Kp_2gAWP6U.jpg",
        date = System.currentTimeMillis(),
        isSucceed = false,
        countryFlagLink = "",
        isFavorite = true
    ),
    LaunchInfo(
        id = 2,
        name = "Thor DM-21 Agena-B | Discoverer 21 (MIDAS RM-2)",
        infoPage = "",
        imageUrl = "",
        date = System.currentTimeMillis(),
        isSucceed = true,
        countryFlagLink = "",
        isFavorite = false
    ),
    LaunchInfo(
        id = 3,
        name = "Thor DM-21 Agena-B | Discoverer 21 (MIDAS RM-2)",
        infoPage = "",
        imageUrl = "",
        date = System.currentTimeMillis(),
        isSucceed = true,
        countryFlagLink = "",
        isFavorite = false
    ),
    LaunchInfo(
        id = 4,
        name = "Thor DM-21 Agena-B | Discoverer 21 (MIDAS RM-2)",
        infoPage = "",
        imageUrl = "",
        date = System.currentTimeMillis(),
        isSucceed = true,
        countryFlagLink = "",
        isFavorite = false
    ), LaunchInfo(
        id = 5,
        name = "Thor DM-21 Agena-B | Discoverer 21 (MIDAS RM-2)",
        infoPage = "",
        imageUrl = "",
        date = System.currentTimeMillis(),
        isSucceed = true,
        countryFlagLink = "",
        isFavorite = false
    )
)
