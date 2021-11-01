package com.pavlo.fedor.compose.flow.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<State> : ViewModel() {
    abstract val stateFlow: StateFlow<State>

    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    protected fun <T> Flow<T>.handleError(action: suspend (Throwable) -> Unit = {}) = catch { error ->
        Timber.d(error)
        action(error)
    }
}
