package com.pavlo.fedor.compose.flow.laucnhes.list.state

import com.pavlo.fedor.compose.domain.model.LaunchInfo

sealed class LaunchesListItemState {

    class InfoItem(val info: LaunchInfo) : LaunchesListItemState()
    object Progress : LaunchesListItemState()
}
