package com.pavlo.fedor.compose.service

import com.pavlo.fedor.compose.domain.service.PaginationService
import com.pavlo.fedor.compose.service.getaway.GetawayModule
import com.pavlo.fedor.compose.service.pagination.OneSidePaginationService
import com.pavlo.fedor.compose.service.sotrage.LaunchesInfoStorageModule
import org.koin.dsl.module

val ServiceModule = module {
    single<PaginationService> { OneSidePaginationService() }
    LaunchesInfoStorageModule(this)
    GetawayModule(this)
}
