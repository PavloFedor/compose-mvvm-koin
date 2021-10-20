package com.pavlo.fedor.compose.flow.laucnhes.state

import com.pavlo.fedor.compose.flow.base.state.BaseSyncStateStore
import com.pavlo.fedor.compose.flow.laucnhes.state.LaunchesNavigationStateAction.OnSelectedNew

internal class LaunchesNavigationStateStore(
    initialStateFactory: LaunchesNavigationInitialStateFactory
) : BaseSyncStateStore<LaunchesNavigationState, LaunchesNavigationMutableState, LaunchesNavigationStateAction>(initialStateFactory) {

    override suspend fun onAction(oldState: LaunchesNavigationMutableState, action: LaunchesNavigationStateAction): LaunchesNavigationMutableState {
        return when (action) {
            is OnSelectedNew -> oldState.updateSelectedItem(action = action)
        }
    }

    private fun LaunchesNavigationMutableState.updateSelectedItem(action: OnSelectedNew): LaunchesNavigationMutableState {
        val newSelected = navigationCells.first { it.id == action.host }
        return copy(selectedCell = newSelected)
    }
}