package com.pavlo.fedor.compose.service.getaway.api

import com.example.utils.mapper.Mapper
import com.pavlo.fedor.compose.domain.error.DomainError
import com.pavlo.fedor.compose.service.getaway.api.base.error.ApiError
import com.pavlo.fedor.compose.service.getaway.error.GetawayError

internal interface GetawayToDomainMapper : Mapper<Unit, GetawayError, DomainError>