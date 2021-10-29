package com.pavlo.fedor.compose.domain.error

class TechnicalError(cause: Throwable?) : DomainError(cause = cause)