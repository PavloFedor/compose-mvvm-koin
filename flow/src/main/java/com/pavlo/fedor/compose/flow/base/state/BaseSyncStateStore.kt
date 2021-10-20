package com.pavlo.fedor.compose.flow.base.state

import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseSyncStateStore<StateValue, MutableStateValue : StateValue, Action>(
    private val initialStateFactory: InitialStateFactory<MutableStateValue>
) : StateStore<StateValue, Action> {

    override val state = MutableStateFlow(initialStateFactory())

    override suspend fun dispatch(action: Action) {
        val newStateValue = onAction(oldState = state.value, action = action)
        state.emit(newStateValue)
    }

    protected abstract suspend fun onAction(oldState: MutableStateValue, action: Action): MutableStateValue
}