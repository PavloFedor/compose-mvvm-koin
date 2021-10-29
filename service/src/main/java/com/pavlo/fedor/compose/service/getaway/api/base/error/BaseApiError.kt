package com.pavlo.fedor.compose.service.getaway.api.base.error

import java.lang.IllegalStateException

sealed class BaseApiError(message: String? = null, cause: Throwable) : ApiError(message = message, cause = cause) {

    class System(cause: Throwable) : BaseApiError(cause = cause, message = cause.message)

    sealed class ServerError(message: String?) : BaseApiError(cause = IllegalStateException(message)) {
        object Unauthorized : ServerError("Unauthorized error!")
        object NotFound : ServerError("Not found error")
        object ToManyRequests : ServerError("To many requests")
        object NoResponseBody : ServerError("No response body")
        class InternalServerError(code: Int) : ServerError("Server return code: $code")
    }
}
