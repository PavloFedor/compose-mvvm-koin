package com.pavlo.fedor.compose.service.getaway

import com.pavlo.fedor.compose.domain.service.RocketLaunchService
import com.pavlo.fedor.compose.service.getaway.api.ApiModule
import com.pavlo.fedor.compose.service.getaway.api.mapper.LaunchDtoToLaunchInfoMapper
import com.pavlo.fedor.compose.service.getaway.api.mapper.LaunchesDtoToLaunchesInfoMapper
import org.koin.core.module.Module

internal val GetawayModule: Module.() -> Unit = {
    ApiModule(this)
    single { LaunchDtoToLaunchInfoMapper() }
    single { LaunchesDtoToLaunchesInfoMapper(launchInfoMapper = get()) }
    single<RocketLaunchService> {
        RocketLaunchServiceImpl(
            apiErrorToDomainMapper = get(),
            launchesInfoMapper = get(),
            launchesApi = get()
        )
    }
}