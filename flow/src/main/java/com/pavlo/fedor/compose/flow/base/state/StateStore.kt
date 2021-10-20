package com.pavlo.fedor.compose.flow.base.state

import kotlinx.coroutines.flow.StateFlow

interface StateStore<StateValue, Action> {

    val state: StateFlow<StateValue>

    suspend fun dispatch(action: Action)

}