package com.pavlo.fedor.compose.domain.error

class ConnectionToServerLost(cause: Throwable) : DomainError(cause = cause)