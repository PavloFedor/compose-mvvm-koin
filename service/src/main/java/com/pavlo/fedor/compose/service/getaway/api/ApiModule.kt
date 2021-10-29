package com.pavlo.fedor.compose.service.getaway.api

import com.pavlo.fedor.compose.service.BuildConfig
import com.pavlo.fedor.compose.service.getaway.api.base.ApiProvider
import com.pavlo.fedor.compose.service.getaway.api.base.RetrofitConnectionErrorInterceptor
import com.pavlo.fedor.compose.service.getaway.api.base.retrofit.DefaultOkHttpClientProvider
import com.pavlo.fedor.compose.service.getaway.api.base.retrofit.OkHttpClientProvider
import com.pavlo.fedor.compose.service.getaway.api.base.retrofit.RetrofitApiProvider
import com.pavlo.fedor.compose.service.getaway.api.launches.LaunchesApi
import org.koin.core.module.Module

internal val ApiModule: Module.() -> Unit = {
    single<ApiProvider> { RetrofitApiProvider(baseUrl = BuildConfig.BASE_API_URL, okHttpClientProvider = get()) }
    single { RetrofitConnectionErrorInterceptor() }
    single<OkHttpClientProvider> { DefaultOkHttpClientProvider(isLoggingEnable = BuildConfig.DEBUG, errorInterceptor = get()) }
    single<GetawayToDomainMapper> { DefaultGetAwayErrorToDomainMapper() }

    single { get<ApiProvider>().provide(LaunchesApi::class.java) }
}
