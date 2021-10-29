package com.pavlo.fedor.compose.service.getaway

import com.pavlo.fedor.compose.domain.error.TechnicalError
import com.pavlo.fedor.compose.service.getaway.api.base.ApiResult
import com.pavlo.fedor.compose.service.getaway.api.GetawayToDomainMapper

internal abstract class RetrofitApiService(protected val apiErrorToDomainMapper: GetawayToDomainMapper) {

    protected suspend fun <Result> execute(apiAction: suspend () -> ApiResult<Result>): Result {
        val apiResult = try {
            apiAction()
        } catch (error: Throwable) {
            throw TechnicalError(error)
        }

        return when (apiResult) {
            is ApiResult.Success -> apiResult.result
            is ApiResult.Failed -> throw apiErrorToDomainMapper(Unit, apiResult.error)
        }
    }
}

