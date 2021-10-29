package com.pavlo.fedor.compose.service.getaway.api.base.retrofit

import com.pavlo.fedor.compose.service.getaway.api.base.ApiResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) return null

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<ApiResult<<Foo>> or Call<ApiResult<out Foo>>"
        }
        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != ApiResult::class.java) return null
        check(responseType is ParameterizedType) {
            "responseType type must be parameterized as ApiResult<<Foo> or ApiResultApiResult<out Foo>"
        }
        val successType = getParameterUpperBound(0, responseType)
        return ApiResultCallAdapter<Any>(successType)
    }
}