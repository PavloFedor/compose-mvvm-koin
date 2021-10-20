package com.pavlo.fedor.compose.flow.laucnhes

import com.pavlo.fedor.compose.flow.laucnhes.state.LaunchesNavigationState
import com.pavlo.fedor.compose.flow.laucnhes.state.LaunchesNavigationStateAction.OnSelectedNew
import com.pavlo.fedor.compose.flow.laucnhes.state.LaunchesNavigationStateStore
import kotlinx.coroutines.flow.StateFlow

internal class LaunchesNavigationViewModelImpl(
    private val stateStore: LaunchesNavigationStateStore
) : LaunchesNavigationViewModel() {

    override val stateFlow: StateFlow<LaunchesNavigationState> get() = stateStore.state

    override fun onSelected(screenId: String) = launch {
        stateStore.dispatch(OnSelectedNew(screenId))
    }
}
