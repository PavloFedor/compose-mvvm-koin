package com.pavlo.fedor.compose.service.getaway.api.base

import com.pavlo.fedor.compose.service.getaway.error.GetawayError


internal sealed class ApiResult<Result> {

    class Success<Result>(val result: Result) : ApiResult<Result>()
    class Failed<Result>(val error: GetawayError) : ApiResult<Result>()
}
