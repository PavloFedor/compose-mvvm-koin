package com.pavlo.fedor.compose.service.getaway.api.base.retrofit

import com.google.gson.GsonBuilder
import com.pavlo.fedor.compose.core.DatePattern
import com.pavlo.fedor.compose.core.DatePattern.ISO_DATE_FORMAT
import com.pavlo.fedor.compose.service.getaway.api.base.ApiProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitApiProvider(
    private val baseUrl: String,
    private val okHttpClientProvider: OkHttpClientProvider
) : ApiProvider {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat(ISO_DATE_FORMAT.value).create()))
            .client(okHttpClientProvider.provide())
            .build()
    }

    override fun <Api : Any> provide(apiClazz: Class<Api>): Api = retrofit.create(apiClazz)
}
