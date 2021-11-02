package com.pavlo.fedor.compose.flow.laucnhes.details.state

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.flow.base.state.InitialStateFactory

class LaunchInfoDetailsInitialStateFactory(private val launchInfo: LaunchInfo) : InitialStateFactory<LaunchInfoDetailsMutableState> {
    override fun invoke(): LaunchInfoDetailsMutableState = LaunchInfoDetailsMutableState(launchInfo)
}
