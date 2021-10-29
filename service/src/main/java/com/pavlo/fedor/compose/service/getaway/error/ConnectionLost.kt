package com.pavlo.fedor.compose.service.getaway.error

import java.io.IOException

sealed class ConnectionLost(cause: Throwable) : IOException(cause), GetawayError {
    class ServerDown(cause: Throwable) : ConnectionLost(cause)
    class NoInternet(cause: Throwable) : ConnectionLost(cause)
}