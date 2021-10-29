package com.pavlo.fedor.compose.service.getaway.api.base.error

import com.pavlo.fedor.compose.service.getaway.error.GetawayError

abstract class ApiError(final override val message: String? = null, final override val cause: Throwable?) : GetawayError, Throwable(message)