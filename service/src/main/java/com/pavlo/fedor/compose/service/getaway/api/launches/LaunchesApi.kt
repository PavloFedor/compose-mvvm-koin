package com.pavlo.fedor.compose.service.getaway.api.launches

import com.pavlo.fedor.compose.service.getaway.api.base.ApiResult
import com.pavlo.fedor.compose.service.getaway.api.launches.Direction.Desc
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

internal interface LaunchesApi {

    companion object {
        private const val LAUNCHES_ENDPOINT = "/2.2.0/launch/"
    }

    @GET(LAUNCHES_ENDPOINT)
    suspend fun query(
        @Query("search") search: String?,
        @Query("ordering") ordering: String = OrderBy.Date(Desc),
        @Query("mode") mode: String = "detailed",
        @Query("net__lte") date: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Long,
    ): ApiResult<LaunchesDto>
}

