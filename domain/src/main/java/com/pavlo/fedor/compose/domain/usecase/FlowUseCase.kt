package com.pavlo.fedor.compose.domain.usecase

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<Params, Result> : UseCase<Params, Flow<Result>>

sealed class UseCaseResult<T> {

    abstract val value: T

    class Succeed<T>(override val value: T) : UseCaseResult<T>()
    class Failed<T>(override val value: T, val error: Throwable) : UseCaseResult<T>()
}
