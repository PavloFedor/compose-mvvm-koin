package com.pavlo.fedor.compose.domain.usecase


interface FetchRocketLaunchesUseCase : FlowUseCase<FetchRocketLaunchesUseCase.Params, Unit> {

    class Params(val query: String?, val refresh: Boolean)
}