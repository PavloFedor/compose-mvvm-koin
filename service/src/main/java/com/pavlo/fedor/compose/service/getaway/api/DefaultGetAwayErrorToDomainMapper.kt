package com.pavlo.fedor.compose.service.getaway.api

import com.pavlo.fedor.compose.domain.error.ConnectionToServerLost
import com.pavlo.fedor.compose.domain.error.DomainError
import com.pavlo.fedor.compose.domain.error.NetworkError
import com.pavlo.fedor.compose.domain.error.TechnicalError
import com.pavlo.fedor.compose.service.getaway.api.base.error.ApiError
import com.pavlo.fedor.compose.service.getaway.error.ConnectionLost
import com.pavlo.fedor.compose.service.getaway.error.GetawayError

internal class DefaultGetAwayErrorToDomainMapper : GetawayToDomainMapper {

    override fun invoke(p: Unit, error: GetawayError): DomainError = when (error) {
        is ConnectionLost.NoInternet -> NetworkError("get launches", error)
        is ConnectionLost.ServerDown -> ConnectionToServerLost(error)
        else -> TechnicalError((error as? Throwable))
    }
}
