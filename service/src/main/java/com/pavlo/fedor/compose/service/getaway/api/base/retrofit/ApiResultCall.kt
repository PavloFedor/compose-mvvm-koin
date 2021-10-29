package com.pavlo.fedor.compose.service.getaway.api.base.retrofit

import com.pavlo.fedor.compose.service.getaway.api.base.ApiResult
import com.pavlo.fedor.compose.service.getaway.api.base.error.ApiError
import com.pavlo.fedor.compose.service.getaway.api.base.error.BaseApiError
import com.pavlo.fedor.compose.service.getaway.api.base.error.BaseApiError.ServerError.NoResponseBody
import com.pavlo.fedor.compose.service.getaway.api.base.error.BaseApiError.System
import com.pavlo.fedor.compose.service.getaway.error.GetawayError
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

internal class ApiResultCall<R>(
    private val apiCallDelegate: Call<R>
) : Call<ApiResult<R>> {

    override fun enqueue(callback: Callback<ApiResult<R>>) {
        apiCallDelegate.enqueue(CallbackWrapper((callback)))
    }

    override fun clone(): ApiResultCall<R> = ApiResultCall<R>(apiCallDelegate.clone())

    override fun execute(): Response<ApiResult<R>> {
        return try {
            Response.success(mapResponse(apiCallDelegate.execute()))
        } catch (error: Throwable) {
            Response.success(ApiResult.Failed((error as? GetawayError) ?: System(error)))
        }
    }

    override fun isExecuted(): Boolean = apiCallDelegate.isExecuted

    override fun cancel() = apiCallDelegate.cancel()

    override fun isCanceled(): Boolean = apiCallDelegate.isCanceled

    override fun request(): Request = apiCallDelegate.request()

    override fun timeout(): Timeout = apiCallDelegate.timeout()

    private inner class CallbackWrapper(private val callback: Callback<ApiResult<R>>) : Callback<R> {
        override fun onResponse(call: Call<R>, response: Response<R>) {
            callback.onResponse(this@ApiResultCall, Response.success(mapResponse(response)))
        }

        override fun onFailure(call: Call<R>, t: Throwable) {
            callback.onResponse(this@ApiResultCall, Response.success(ApiResult.Failed((t as? GetawayError) ?: System(t))))
        }
    }

    private fun mapResponse(response: Response<R>): ApiResult<R> {
        val code = response.code()
        val body = response.body()
        return when {
            code == 401 -> ApiResult.Failed(BaseApiError.ServerError.Unauthorized)
            code == 404 -> ApiResult.Failed(BaseApiError.ServerError.NotFound)
            code == 429 -> ApiResult.Failed(BaseApiError.ServerError.ToManyRequests)
            code in 500..526 -> ApiResult.Failed(BaseApiError.ServerError.InternalServerError(code))
            body == null -> ApiResult.Failed(NoResponseBody)
            else -> ApiResult.Success(body)
        }
    }
}