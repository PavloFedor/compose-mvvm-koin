package com.pavlo.fedor.compose.service.getaway.api.base.retrofit

import okhttp3.OkHttpClient

internal interface OkHttpClientProvider {

    fun provide(): OkHttpClient
}
