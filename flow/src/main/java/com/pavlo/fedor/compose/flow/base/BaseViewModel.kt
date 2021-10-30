package com.pavlo.fedor.compose.flow.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State> : ViewModel() {
    abstract val stateFlow: StateFlow<State>

    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}