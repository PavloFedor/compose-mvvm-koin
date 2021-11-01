package com.pavlo.fedor.compose.domain.storage.base

import kotlinx.coroutines.flow.Flow


interface ObservableStorage<Value> {

    fun observe(): Flow<Value>
}
