package com.pavlo.fedor.compose.domain.usecase

interface UseCase<Params, Result> {

    suspend operator fun invoke(params: Params): Result
}
