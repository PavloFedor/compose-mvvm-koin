package com.pavlo.fedor.compose.domain.usecase

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<Params, Result> : UseCase<Params, Flow<Result>>