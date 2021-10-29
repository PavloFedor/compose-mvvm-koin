package com.pavlo.fedor.compose.service.getaway.api.base

import com.pavlo.fedor.compose.service.getaway.error.ConnectionLost
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class RetrofitConnectionErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = try {
        chain.proceed(chain.request())
    } catch (error: SocketTimeoutException) {
        throw chain.onConnectionLost(error)
    } catch (error: SocketException) {
        throw chain.onConnectionLost(error)
    } catch (error: UnknownHostException) {
        throw chain.onConnectionLost(error)
    }

    private fun Interceptor.Chain.onConnectionLost(error: Throwable): ConnectionLost = try {
        val pingRequest = Request.Builder().url("https://clients3.google.com/generate_204").build()
        val response = proceed(pingRequest)
        if (response.code in 200..204) {
            ConnectionLost.ServerDown(error)
        } else {
            ConnectionLost.NoInternet(error)
        }
    } catch (error: Throwable) {
        ConnectionLost.NoInternet(error)
    }
}