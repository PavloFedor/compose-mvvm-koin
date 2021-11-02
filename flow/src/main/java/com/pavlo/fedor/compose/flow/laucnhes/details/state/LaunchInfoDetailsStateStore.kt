package com.pavlo.fedor.compose.flow.laucnhes.details.state

import com.pavlo.fedor.compose.flow.base.state.BaseSyncStateStore

class LaunchInfoDetailsStateStore(
    initialStateFactory: LaunchInfoDetailsInitialStateFactory
) : BaseSyncStateStore<LaunchInfoDetailsState, LaunchInfoDetailsMutableState, LaunchInfoDetailsStateAction>(initialStateFactory) {

    override suspend fun onAction(oldState: LaunchInfoDetailsMutableState, action: LaunchInfoDetailsStateAction) = when (action) {
        is LaunchInfoDetailsStateAction.OnItemChanged -> oldState.copy(launchInfo = action.item)
    }
}