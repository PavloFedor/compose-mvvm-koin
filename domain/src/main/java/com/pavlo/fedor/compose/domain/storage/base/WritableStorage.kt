package com.pavlo.fedor.compose.domain.storage.base

interface WritableStorage<in Value, out RValue> {
    suspend fun set(value: Value): RValue
}
