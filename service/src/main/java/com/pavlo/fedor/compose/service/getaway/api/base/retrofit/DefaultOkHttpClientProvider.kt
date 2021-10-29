package com.pavlo.fedor.compose.service.getaway.api.base.retrofit

import com.pavlo.fedor.compose.service.getaway.api.base.RetrofitConnectionErrorInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import java.util.concurrent.TimeUnit

internal class DefaultOkHttpClientProvider(
    private val isLoggingEnable: Boolean,
    private val errorInterceptor: RetrofitConnectionErrorInterceptor
) : OkHttpClientProvider {

    companion object {
        const val READ_TIMEOUT_SECONDS = 60L
        const val CONNECTION_TIMEOUT_SECONDS = 60L
        const val WRITE_TIMEOUT_SECONDS = 60L
    }

    override fun provide(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (isLoggingEnable) BODY else NONE))
            .addInterceptor(errorInterceptor)
            .build()
    }
}
