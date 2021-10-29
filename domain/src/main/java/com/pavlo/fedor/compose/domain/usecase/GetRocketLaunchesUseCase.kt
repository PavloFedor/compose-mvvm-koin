package com.pavlo.fedor.compose.domain.usecase

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page

interface GetRocketLaunchesUseCase : FlowUseCase<GetRocketLaunchesUseCase.Params, Page<LaunchInfo>> {

    class Params(val query: String?, val refresh: Boolean)
}
