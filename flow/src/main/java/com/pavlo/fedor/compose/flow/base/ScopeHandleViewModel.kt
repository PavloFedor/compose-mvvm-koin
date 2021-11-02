package com.pavlo.fedor.compose.flow.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.core.scope.Scope
import timber.log.Timber
import java.lang.IllegalStateException

class ScopeHandleViewModel(private val scopes: List<Scope>) : ViewModel() {

    init {
        Timber.d("Created: ${scopes.joinToString { it.scopeQualifier.value }}")
    }

    override fun onCleared() {
        super.onCleared()
        scopes.forEach { it.close() }
        Timber.d("onCleared: ${scopes.joinToString { it.scopeQualifier.value }}")
    }
}

class ScopeHandleViewModelFactory(private val scopes: () -> List<Scope>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == ScopeHandleViewModel::class.java) {
            return ScopeHandleViewModel(scopes = scopes()) as T
        }
        throw IllegalStateException("Only ScopeHandleViewModel is supported")
    }
}