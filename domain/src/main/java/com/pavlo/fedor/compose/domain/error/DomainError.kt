package com.pavlo.fedor.compose.domain.error

abstract class DomainError(final override val message: String? = null, final override val cause: Throwable?) : Throwable()