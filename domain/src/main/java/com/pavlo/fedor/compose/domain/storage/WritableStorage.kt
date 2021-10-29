package com.pavlo.fedor.compose.domain.storage

interface WritableStorage<Value> {
    suspend fun set(value: Value)
}
