package com.pavlo.fedor.compose.flow.laucnhes.details

import com.pavlo.fedor.compose.domain.model.LaunchInfo

sealed class DetailArgs {

    abstract val launchInfo: LaunchInfo

    class History(override val launchInfo: LaunchInfo) : DetailArgs()
    class Favourite(override val launchInfo: LaunchInfo) : DetailArgs()
}