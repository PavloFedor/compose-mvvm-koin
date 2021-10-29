package com.pavlo.fedor.compose.domain.error

class NetworkError(apiName: String, cause: Throwable) : DomainError("During execute $apiName", cause)