package com.pavlo.fedor.compose.flow.laucnhes.details.state

import com.pavlo.fedor.compose.domain.model.LaunchInfo

sealed class LaunchInfoDetailsStateAction {

    class OnItemChanged(val item: LaunchInfo) : LaunchInfoDetailsStateAction()
}
