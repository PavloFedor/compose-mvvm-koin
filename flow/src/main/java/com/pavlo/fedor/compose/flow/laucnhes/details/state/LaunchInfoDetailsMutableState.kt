package com.pavlo.fedor.compose.flow.laucnhes.details.state

import com.pavlo.fedor.compose.domain.model.LaunchInfo

data class LaunchInfoDetailsMutableState(override val launchInfo: LaunchInfo) : LaunchInfoDetailsState